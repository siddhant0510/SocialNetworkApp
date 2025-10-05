package com.example.socialnetworkapp.presentation.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.socialnetworkapp.domain.models.Post
import com.example.socialnetworkapp.presentation.main_feed.MainFeedScreen
import com.example.socialnetworkapp.presentation.login.LoginScreen
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
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
){
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Screen.SplashScreen.route){
            SplashScreen(
                onPopBackStack = navController::popBackStack,
                onNavigate = navController::navigate
            )
        }
        composable(Screen.LoginScreen.route){
            LoginScreen(
                onNavigate = navController::navigate,
                snackbarHostState = snackbarHostState
            )
        }
        composable(Screen.RegisterScreen.route){
            RegisterScreen(
                navController = navController,
            )
        }
        composable(Screen.MainFeedScreen.route){
            MainFeedScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                scaffoldState = snackbarHostState
            )
        }
        composable(Screen.ChatScreen.route){
            ChatScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
            )
        }
        composable(Screen.ActivityScreen.route){
            ActivityScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
            )
        }
        composable(
            route = Screen.ProfileScreen.route + "?userId={userId}",
            arguments = listOf(
                navArgument(name = "userId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ){
            ProfileScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                snackbarHostState = snackbarHostState
            )
        }
        composable(Screen.EditProfileScreen.route){
            EditProfileScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
            )
        }
        composable(Screen.CreatePostScreen.route){
            CreatePostScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                scaffoldState = snackbarHostState
            )
        }
        composable(Screen.SearchScreen.route){
            SearchScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
            )
        }
        composable(Screen.PostDetailsScreen.route){
            PostDetailScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
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
            PersonListScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
            )
        }
    }
}