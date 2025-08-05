package com.example.socialnetworkapp.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.models.User
import com.example.socialnetworkapp.presentation.componenets.Post
import com.example.socialnetworkapp.presentation.profile.components.BannerSection
import com.example.socialnetworkapp.presentation.profile.components.ProfileHeaderSection
import com.example.socialnetworkapp.presentation.ui.theme.ProfilePictureSizeLarge
import com.example.socialnetworkapp.presentation.ui.theme.SpaceMedium
import com.example.socialnetworkapp.presentation.util.Screen

//import com.example.socialnetworkapp.presentation.componenets.StandardScaffold

@Composable
fun ProfileScreen(navController: NavController){
    Column(modifier = Modifier.fillMaxWidth().padding(top = 48.dp)){
        var toolbarOffSetY by remember {
            mutableStateOf(0f)
        }
        val bannerHeight = (LocalConfiguration.current.screenWidthDp / 2.5f).dp
        val toolbarHeightExpanded = remember {
            bannerHeight + ProfilePictureSizeLarge
        }
        val toolbarHeightCollapsed = 56.dp
        val nestedScrollConnection = remember {
            object : NestedScrollConnection{
                override fun onPreScroll(available: Offset, source: NestedScrollSource) : Offset{
                    val delta = available.y
                    val newOffSet = toolbarOffSetY + delta

                    return super.onPreScroll(available, source)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize(),
            ){
                item {
                    Spacer(modifier = Modifier.height(toolbarHeightExpanded - ProfilePictureSizeLarge/2f))
                }
                item {
                    ProfileHeaderSection(
                        user = User(
                            profilePictureUrl = "",
                            username = "Siddhant Kudale",
                            description = "This is description This is description This is description This is description This is description This is descriptionThis is description This is description",
                            followerCount = 233,
                            followingCount = 900,
                            postCount = 23
                        )
                    )
                }
                items(20){
                    Spacer(
                        modifier = Modifier
                            .height(SpaceMedium),
                    )
                    Post(
                        post = com.example.socialnetworkapp.domain.models.Post(
                            username = "Siddhant Kudale",
                            imageUrl = "",
                            profilePictureUrl = "",
                            description = "This is a description This is a description " +
                                    "This is a description This is a description This is " +
                                    "a description This is a description This is a description...",
                            likeCount = 17,
                            commentCount = 7
                        ),
                        showProfileImage = false,
                        onPostClick = {
                            navController.navigate(Screen.PostDetailsScreen.route)
                        },
                    )
                }
            }
            Column (
                modifier = Modifier
                    .align(Alignment.TopCenter)
            ){
                BannerSection(
                    modifier = Modifier.height(bannerHeight)
                )
                Image(
                    painter = painterResource(id = R.drawable.philipp),
                    contentDescription = stringResource(id = R.string.profile_image),
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .graphicsLayer {
                            translationY = -ProfilePictureSizeLarge.toPx() / 2f
                        }
                        .size(ProfilePictureSizeLarge)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface,
                            shape = CircleShape
                        )
                )
            }
        }
    }
}