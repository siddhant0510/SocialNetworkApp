package com.example.socialnetworkapp.feature_post.presentation.post_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.util.showKeyboard
import com.example.socialnetworkapp.presentation.componenets.ActionRow
import com.example.socialnetworkapp.presentation.componenets.SendTextField
import com.example.socialnetworkapp.presentation.componenets.StandardToolbar
import com.example.socialnetworkapp.presentation.theme.MediumGray
import com.example.socialnetworkapp.presentation.theme.ProfilePictureSizeExtraSmall
import com.example.socialnetworkapp.presentation.theme.ProfilePictureSizeMedium
import com.example.socialnetworkapp.presentation.theme.SpaceLarge
import com.example.socialnetworkapp.presentation.theme.SpaceMedium
import com.example.socialnetworkapp.presentation.theme.SpaceSmall
import com.example.socialnetworkapp.presentation.util.UiEvent
import com.example.socialnetworkapp.presentation.util.asString
import com.example.socialnetworkapp.utli.Screen
import com.example.socialnetworkapp.utli.sendSharePostIntent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PostDetailScreen(
    snackbarHostState: SnackbarHostState,
    imageLoader: ImageLoader,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: PostDetailViewModel = hiltViewModel(),
    shouldShowKeyboard: Boolean = false
) {
    val state = viewModel.state.value
    val commentTextFieldState = viewModel.commentTextFieldState.value

    val focusedRequester = remember {
        FocusRequester()
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        if(shouldShowKeyboard) {
            context.showKeyboard()
            focusedRequester.requestFocus()
        }
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.ShowSnakbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
                is UiEvent.Navigate -> Unit
                UiEvent.NavigateUp -> Unit
                UiEvent.OnLogin -> Unit
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
                                        model = state.post.imageUrl,
                                        imageLoader = imageLoader
                                    ),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = state.post.description,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(16f / 9f)
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
                                            context.showKeyboard()
                                            focusedRequester.requestFocus()
                                        },
                                        onShareClick = {
                                            context.sendSharePostIntent(post.id)
                                        },
                                        onUsernameClick = {
                                            onNavigate(Screen.ProfileScreen.route + "?userId=${post.userId}")
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
                                            id = R.string.x_likes,
                                            post.likeCount
                                        ),
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.clickable {
                                            onNavigate(Screen.PersonListScreen.route + "/${post.id}")
                                        }
                                    )
                                }
                            }
                        }
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = state.post?.imageUrl,
                                imageLoader = imageLoader
                            ),
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
                    imageLoader = imageLoader,
                    comment = comment,
                    onLikeClick = {
                        viewModel.onEvent(PostDetailEvent.LikeComment(comment.id))
                    },
                    onLikedByClick =  {
                        onNavigate(Screen.PersonListScreen.route + "/${comment.id}")
                    }
                )
            }
        }
        SendTextField(
            state = viewModel.commentTextFieldState.value,
            onValueChange = {
                viewModel.onEvent(PostDetailEvent.EnteredComment(it))
            },
            onSend = {
                viewModel.onEvent(PostDetailEvent.Comment)
            },
            hint = stringResource(id = R.string.enter_a_comment),
            isLoading = viewModel.commentState.value.isLoading,
            focusedRequester = focusedRequester
        )
    }
}