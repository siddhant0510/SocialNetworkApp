package com.example.socialnetworkapp.feature_post.presentation.post_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest.Builder
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.presentation.componenets.ActionRow
import com.example.socialnetworkapp.presentation.componenets.StandardTextField
import com.example.socialnetworkapp.presentation.componenets.StandardToolbar
import com.example.socialnetworkapp.theme.MediumGray
import com.example.socialnetworkapp.theme.ProfilePictureSizeExtraSmall
import com.example.socialnetworkapp.theme.ProfilePictureSizeMedium
import com.example.socialnetworkapp.theme.SpaceLarge
import com.example.socialnetworkapp.theme.SpaceMedium
import com.example.socialnetworkapp.theme.SpaceSmall
import com.example.socialnetworkapp.utilNew.UiEvent
import com.example.socialnetworkapp.utilNew.asString
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PostDetailScreen(
    snackbarHostState: SnackbarHostState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: PostDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val commentTextFieldState = viewModel.commentTextFieldState.value

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.ShowSnakbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
                is UiEvent.Navigate -> TODO()
                UiEvent.NavigateUp -> TODO()
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp)
    ) {
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = stringResource(id = R.string.your_feed),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true,
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.surface),
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Spacer(modifier = Modifier.Companion.height(SpaceLarge))
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .offset(y = ProfilePictureSizeExtraSmall / 2f)
                                .clip(MaterialTheme.shapes.medium)
                                .shadow(5.dp)
                                .background(MediumGray)
                        ) {
                            state.post?.let { post ->
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        Builder(LocalContext.current).data(
                                            data = state.post.imageUrl
                                        ).apply(block = { -> crossfade(true) }).build()),
                                    contentDescription = state.post.description,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(SpaceLarge)
                                ) {
                                    ActionRow(
                                        username = state.post.username,
                                        modifier = Modifier.fillMaxWidth(),
                                        onLikedClick = {
                                            viewModel.onEvent(PostDetailEvent.LikePost)
                                        },
                                        onCommentClick = {

                                        },
                                        onShareClick = {

                                        },
                                        onUsernameClick = {

                                        },
                                        isLiked = state.post.isLiked
                                    )
                                    Spacer(modifier = Modifier.Companion.height(SpaceSmall))
                                    Text(
                                        text = state.post.description,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                    Spacer(modifier = Modifier.Companion.height(SpaceMedium))
                                    Text(
                                        text = stringResource(
                                            id = R.string.liked_by_x_people,
                                            post.likeCount
                                        ),
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                        Image(
                            painter = rememberAsyncImagePainter(
                                Builder(LocalContext.current).data(
                                    data = state.post?.profilePictureUrl
                                ).apply(block = { -> crossfade(true) }).build()),
                            contentDescription = "Profile picture",
                            modifier = Modifier
                                .offset(y = -(ProfilePictureSizeMedium / 6f))
                                .size(ProfilePictureSizeMedium)
                                .clip(CircleShape)
                                .align(Alignment.TopCenter)
                        )
                        if(state.isLoadingPost) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.Companion.height(SpaceLarge))
            }
            items(
                count = state.comments.size
            ) { index ->
                val comment = state.comments[index]
                Comment(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = SpaceLarge,
                            vertical = SpaceSmall
                        ),
                    comment = comment,
                    onLikeClick = {
                        viewModel.onEvent(PostDetailEvent.LikeComment(comment.id))
                    }
                )
            }
        }
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .padding(SpaceLarge),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StandardTextField(
                text = commentTextFieldState.text,
                onValueChange = {
                    viewModel.onEvent(PostDetailEvent.EnteredComment(it))
                },
                modifier = Modifier.weight(1f),
                hint = stringResource(id = R.string.enter_a_comment)
            )
            if(viewModel.commentState.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .size(24.dp),
                    strokeWidth = 2.dp
                )
            } else {
                IconButton(
                    onClick = {
                        viewModel.onEvent(PostDetailEvent.Comment)
                    },
                    enabled = commentTextFieldState.error == null
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        tint = if(commentTextFieldState.error == null) {
                            MaterialTheme.colorScheme.primary
                        } else MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(id = R.string.send_comment)
                    )
                }
            }
        }
    }
}