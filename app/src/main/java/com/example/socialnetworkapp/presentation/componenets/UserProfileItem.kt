package com.example.socialnetworkapp.presentation.componenets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.example.socialnetworkapp.domain.models.UserItem
import com.example.socialnetworkapp.presentation.theme.ProfilePictureSizeSmall
import com.example.socialnetworkapp.presentation.theme.SpaceMedium
import com.example.socialnetworkapp.presentation.theme.SpaceSmall

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserProfileItem (
    user: UserItem,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier,
    actionIcon: @Composable () -> Unit = {},
    onItemClick: () -> Unit = {},
    onActionItemClick: () -> Unit = {},
    ownUserId: String = ""
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        onClick = onItemClick,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colorScheme.surface
    ){
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = SpaceSmall,
                    horizontal = SpaceMedium
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Image(
                painter = rememberAsyncImagePainter(
                    model = user.profilePictureUrl,
                    imageLoader = imageLoader
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(ProfilePictureSizeSmall)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = SpaceSmall)
                    .weight(1f)
            ){
                Text(
                    text = user.username,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(SpaceSmall))
                Text(
                    text = user.bio,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    modifier = Modifier.heightIn(
                        min = MaterialTheme.typography.bodyMedium.fontSize.value.dp * 3f
                    )
                )
            }
            if(user.userId != ownUserId) {
                IconButton(
                    onClick = onActionItemClick,
                    modifier = Modifier
                        .size(25.dp)
                ) {
                    actionIcon()
                }
            }
        }
    }
}