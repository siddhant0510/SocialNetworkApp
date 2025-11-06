package com.example.socialnetworkapp.presentation.feature_post.presentation.post_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.request.ImageRequest.Builder
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.models.Comment
import com.example.socialnetworkapp.presentation.componenets.ActionRow
import com.example.socialnetworkapp.presentation.componenets.StandardToolbar
import com.example.socialnetworkapp.presentation.ui.theme.MediumGray
import com.example.socialnetworkapp.presentation.ui.theme.ProfilePictureSizeExtraSmall
import com.example.socialnetworkapp.presentation.ui.theme.ProfilePictureSizeMedium
import com.example.socialnetworkapp.presentation.ui.theme.SpaceLarge
import com.example.socialnetworkapp.presentation.ui.theme.SpaceMedium
import com.example.socialnetworkapp.presentation.ui.theme.SpaceSmall

@Composable
fun PostDetailScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: PostDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
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
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Spacer(modifier = Modifier.height(SpaceLarge))
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
                                        username = "Philipp Lackner",
                                        modifier = Modifier.fillMaxWidth(),
                                        onLikedClick = { isLiked ->

                                        },
                                        onCommentClick = {

                                        },
                                        onShareClick = {

                                        },
                                        onUsernameClick = { username ->

                                        }
                                    )
                                    Spacer(modifier = Modifier.height(SpaceSmall))
                                    Text(
                                        text = state.post.description,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                    Spacer(modifier = Modifier.height(SpaceMedium))
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
                Spacer(modifier = Modifier.height(SpaceLarge))
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
                    comment = comment
                )
            }
        }
    }
}

@Composable
fun Comment(
    modifier: Modifier = Modifier,
    comment: Comment,
    onLikeClick: (Boolean) -> Unit = {}
) {
    Card(
        modifier = modifier,
        elevation = 5.dp,
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colorScheme.onSurface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceMedium)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            Builder(LocalContext.current).data(
                            data = comment.profilePictureUrl
                        ).apply(block = { -> crossfade(true) }).build()),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(ProfilePictureSizeExtraSmall)
                    )
                    Spacer(modifier = Modifier.width(SpaceSmall))
                    Text(
                        text = comment.username,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(
                    text = comment.formattedTime,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(SpaceMedium))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = comment.comment,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(9f)
                )
                Spacer(modifier = Modifier.width(SpaceMedium))
                IconButton(
                    onClick = {
                        onLikeClick(comment.isLiked)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        tint = if (comment.isLiked) MaterialTheme.colorScheme.primary
                         else MaterialTheme.colorScheme.onBackground,
                        contentDescription = if (comment.isLiked) {
                            stringResource(id = R.string.unlike)
                        } else stringResource(id = R.string.like)
                    )
                }
            }
            Spacer(modifier = Modifier.height(SpaceMedium))
            Text(
                text = stringResource(id = R.string.liked_by_x_people, comment.likedCount),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}