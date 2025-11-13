package com.example.socialnetworkapp.presentation.componenets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest.Builder
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.models.Post
import com.example.socialnetworkapp.theme.HintGray
import com.example.socialnetworkapp.theme.MediumGray
import com.example.socialnetworkapp.theme.ProfilePictureSizeMedium
import com.example.socialnetworkapp.theme.SpaceMedium
import com.example.socialnetworkapp.theme.SpaceSmall
import com.example.socialnetworkapp.theme.TextWhite
import com.example.socialnetworkapp.utli.Constants

@Composable
fun Post(
    post: Post,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    imageLoader: ImageLoader,
    showProfileImage: Boolean = true,
    onPostClick: () -> Unit = {},
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onUsernameClick: () -> Unit = {}

){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = SpaceSmall,
                bottom = SpaceMedium,
                start = SpaceMedium,
                end = SpaceMedium
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = if(showProfileImage) ProfilePictureSizeMedium / 2f else 0.dp)
                .clip(MaterialTheme.shapes.medium)
                .shadow(5.dp)
                .background(MediumGray)
                .clickable {
                    onPostClick()
                }
        ){
            Image(
                painter = rememberAsyncImagePainter(
                    model = post.imageUrl,
                    imageLoader = imageLoader
                ),
                contentDescription = "Post1",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceMedium)
            ){
                ActionRow(
                    username = post.username,
                    modifier = Modifier.fillMaxWidth(),
                    isLiked = post.isLiked,
                    onLikedClick = onLikeClick,
                    onCommentClick = onCommentClick,
                    onShareClick = onShareClick,
                    onUsernameClick = onUsernameClick
                )
                Spacer(modifier = Modifier.height(SpaceSmall))
                Text(
                    text = buildAnnotatedString {
                        append(post.description)
                        withStyle(SpanStyle(
                            color = HintGray
                        )) {
                            append(
                                " " + LocalContext.current.getString(
                                    R.string.read_mode
                                )
                            )
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = Constants.MAX_POST_DESCRIPTION_LINES
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = stringResource(
                            id = R.string.x_likes,
                            post.likeCount
                        ),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Text(
                        text = stringResource(
                            id = R.string.x_comments,
                            post.commentCount
                        ),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                }
            }

        }
        if(showProfileImage){
            Image(
                painter = rememberAsyncImagePainter(
                    model = post.profilePictureUrl,
                    imageLoader = imageLoader
                ),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .offset(y = (ProfilePictureSizeMedium / 12f))
                    .size(ProfilePictureSizeMedium)
                    .clip(CircleShape)
                    .align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun EngagementButtons(
    modifier: Modifier = Modifier,
    isLiked: Boolean = false,
    iconSize: Dp = 30.dp,
    onLikedClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {}
){
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ){
        IconButton(
            onClick = { onLikedClick() },
            modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                tint = if(isLiked){
                    MaterialTheme.colorScheme.primary
                } else {
                    TextWhite
                },
                contentDescription = if(isLiked) {
                    stringResource(id = R.string.unlike)
                } else {
                    stringResource(id = R.string.like)
                }
            )
        }
        Spacer(modifier = Modifier.width(SpaceSmall))
        IconButton(
            onClick = { onCommentClick() },
            modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Comment,
                contentDescription = stringResource(id = R.string.comment)
            )
        }
        Spacer(modifier = Modifier.width(SpaceSmall))
        IconButton(
            onClick = { onShareClick() },
            modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = stringResource(id = R.string.share)
            )
        }
    }
}

@Composable
fun ActionRow(
    modifier: Modifier = Modifier,
    isLiked: Boolean = false,
    onLikedClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    username: String?,
    onUsernameClick: () -> Unit = {},

    ){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ){
        Text(
            text = username.toString(),
            fontSize = 18.sp,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .clickable {
                    onUsernameClick()
                }
        )
        EngagementButtons(
            isLiked = isLiked,
            onLikedClick = onLikedClick,
            onCommentClick = onCommentClick,
            onShareClick = onShareClick
        )
    }
}
