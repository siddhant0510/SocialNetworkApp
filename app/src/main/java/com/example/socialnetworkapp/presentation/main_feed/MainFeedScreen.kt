package com.example.socialnetworkapp.presentation.main_feed

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.socialnetworkapp.presentation.componenets.Post
import com.example.socialnetworkapp.presentation.componenets.StandardScaffold

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainFeedScreen(
    navController: NavController
){
    Post(
        post = com.example.socialnetworkapp.domain.models.Post(
            username = "Siddhant",
            imageUrl = "",
            profilePictureUrl = "",
            description = "This is a description This is a description " +
                    "This is a description This is a description This is " +
                    "a description This is a description This is a description...",
            likeCount = 17,
            commentCount = 7
        )
    )
}