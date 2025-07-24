package com.example.socialnetworkapp.presentation.main_feed

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.socialnetworkapp.presentation.componenets.Post

@Composable
fun MainFeedScreen(
    navController: NavController
){
    Post(
        post = com.example.socialnetworkapp.domain.models.Post(
            username = "Siddhant Kudale",
            imageUrl = "",
            profilePictureUrl = "",
            description = "Lorem Ipsum is simply dummy text of the printing" +
                    " and typesetting industry. Lorem Ipsum has been the " +
                    "industry's standard dummy text ever since the 1500s...",
            likeCount = 17,
            commentCount = 7
        )
    )
}