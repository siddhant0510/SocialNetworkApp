package com.example.socialnetworkapp.presentation.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.socialnetworkapp.domain.models.Post
import com.example.socialnetworkapp.presentation.main_feed.MainFeedScreen
import com.example.socialnetworkapp.login.LoginScreen
import com.example.socialnetworkapp.presentation.edit_profile.EditProfileScreen
import com.example.socialnetworkapp.presentation.activity.ActivityScreen
import com.example.socialnetworkapp.presentation.chat.ChatScreen
import com.example.socialnetworkapp.presentation.create_post.CreatePostScreen
import com.example.socialnetworkapp.presentation.person_list.PersonListScreen
import com.example.socialnetworkapp.presentation.post_detail.PostDetailScreen
import com.example.socialnetworkapp.presentation.profile.ProfileScreen
import com.example.socialnetworkapp.presentation.register.RegisterScreen
import com.example.socialnetworkapp.presentation.search.SearchScreen
import com.example.socialnetworkapp.presentation.splash.SplashScreen

@Composable
fun Navigation(
    navController: NavHostController
){
    //val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Screen.SplashScreen.route){
            SplashScreen(navController = navController)
        }
        composable(Screen.LoginScreen.route){
            LoginScreen(navController = navController)
        }
        composable(Screen.RegisterScreen.route){
            RegisterScreen(navController = navController)
        }
        composable(Screen.MainFeedScreen.route){
            MainFeedScreen(navController = navController)
        }
        composable(Screen.ChatScreen.route){
            ChatScreen(navController = navController)
        }
        composable(Screen.ActivityScreen.route){
            ActivityScreen(navController = navController)
        }
        composable(Screen.ProfileScreen.route){
            ProfileScreen(navController = navController)
        }
        composable(Screen.EditProfileScreen.route){
            EditProfileScreen(navController = navController)
        }
        composable(Screen.CreatePostScreen.route){
            CreatePostScreen(navController = navController)
        }
        composable(Screen.SearchScreen.route){
            SearchScreen(navController = navController)
        }
        composable(Screen.PostDetailsScreen.route){
            PostDetailScreen(
                navController = navController,
                post = Post(
                    username = "Siddhant Kudale",
                    imageUrl = "",
                    profilePictureUrl = "",
                    description = "This is a description This is a description This is a description This is a descriptionThis is a descriptionThis is a descriptionThis is a descriptionThis is a descriptionThis is a descriptionThis is a descriptionThis is a description This is a description This is a description This is a description This is a description This is a description ",
                    likeCount = 17,
                    commentCount = 7
                )
            )
        }
        composable(Screen.PersonListScreen.route){
            PersonListScreen(navController = navController)
        }
    }
}