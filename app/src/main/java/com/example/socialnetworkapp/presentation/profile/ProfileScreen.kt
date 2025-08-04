package com.example.socialnetworkapp.presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.models.Activity
import com.example.socialnetworkapp.domain.models.User
import com.example.socialnetworkapp.domain.util.ActivityAction
import com.example.socialnetworkapp.domain.util.DateFormatUtil
import com.example.socialnetworkapp.presentation.activity.ActivityItem
import com.example.socialnetworkapp.presentation.componenets.Post
import com.example.socialnetworkapp.presentation.componenets.StandardToolbar
import com.example.socialnetworkapp.presentation.profile.components.BannerSection
import com.example.socialnetworkapp.presentation.profile.components.ProfileHeaderSection
import com.example.socialnetworkapp.presentation.ui.theme.ProfilePictureSizeLarge
import com.example.socialnetworkapp.presentation.ui.theme.SpaceMedium
import com.example.socialnetworkapp.presentation.util.Screen
import kotlin.random.Random

//import com.example.socialnetworkapp.presentation.componenets.StandardScaffold

@Composable
fun ProfileScreen(navController: NavController){
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 48.dp)
    ){
        StandardToolbar(
            navController = navController,
            title = {
                Text(
                    text = stringResource(id = R.string.your_profile),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false,
        )
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
        ){
            item{
                BannerSection(
                    modifier = Modifier
                        .aspectRatio(2.5f)
                )
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
                        .height(SpaceMedium)
                        .offset(y = -ProfilePictureSizeLarge/2f),
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
                    modifier = Modifier
                        .offset(y = -ProfilePictureSizeLarge /2f),
                )
            }
        }
    }
}