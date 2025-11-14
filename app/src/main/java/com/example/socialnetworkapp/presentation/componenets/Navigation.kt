package com.example.socialnetworkapp.presentation.componenets

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.ImageLoader
import com.example.socialnetworkapp.feature_activity.presentation.ActivityScreen
import com.example.socialnetworkapp.feature_chat.ChatScreen
import com.example.socialnetworkapp.feature_post.presentation.create_post.CreatePostScreen
import com.example.socialnetworkapp.feature_post.presentation.main_feed.MainFeedScreen
import com.example.socialnetworkapp.feature_post.presentation.person_list.PersonListScreen
import com.example.socialnetworkapp.feature_post.presentation.post_detail.PostDetailScreen
import com.example.socialnetworkapp.feature_profile.presentation.edit_profile.EditProfileScreen
import com.example.socialnetworkapp.feature_profile.presentation.profile.ProfileScreen
import com.example.socialnetworkapp.feature_profile.presentation.search.SearchScreen
import com.example.socialnetworkapp.feature_auth.presentation.login.LoginScreen
import com.example.socialnetworkapp.feature_auth.presentation.register.RegisterScreen
import com.example.socialnetworkapp.feature_auth.presentation.splash.SplashScreen
import com.example.socialnetworkapp.utli.Screen

@Composable
fun Navigation(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    imageLoader: ImageLoader
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
                onNavigate = { },
                onLogin = {
                    navController.popBackStack(
                        route = Screen.MainFeedScreen.route,
                        inclusive = false
                    )
                },
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
                snackbarHostState = snackbarHostState,
                imageLoader = imageLoader
            )
        }
        composable(Screen.ChatScreen.route){
            ChatScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                imageLoader = imageLoader
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
                userId = it.arguments?.getString("userId"),
                onLogout = navController::navigateUp,
                onNavigate = navController::navigate,
                snackbarHostState = snackbarHostState,
                imageLoader = imageLoader
            )
        }
        composable(
            Screen.EditProfileScreen.route + "/{userId}",
            arguments = listOf(
                navArgument(name = "userId") {
                    type = NavType.StringType
                }
            )
        ){
            EditProfileScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                snackbarHostState = snackbarHostState,
                imageLoader = imageLoader
            )
        }
        composable(Screen.CreatePostScreen.route){
            CreatePostScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                scaffoldState = snackbarHostState,
                imageLoader = imageLoader
            )
        }
        composable(Screen.SearchScreen.route){
            SearchScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                imageLoader = imageLoader
            )
        }
        composable(
            route = Screen.PostDetailsScreen.route + "/{postId}?shouldShowKeyboard={shouldShowKeyboard}",
            arguments = listOf(
                navArgument(
                    name = "postId"
                ) {
                    type = NavType.StringType
                },
                navArgument(
                    name = "shouldShowKeyboard"
                ) {
                    type = NavType.StringType
                    defaultValue = false
                }
            )
        ){
            val shouldShowKeyboard = it.arguments?.getBoolean("shouldShowKeyboard") ?: false
            PostDetailScreen(
                snackbarHostState = snackbarHostState,
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                shouldShowKeyboard = shouldShowKeyboard,
                imageLoader = imageLoader
            )
        }
        composable(
            route = Screen.PersonListScreen.route + "/{parentId}",
            arguments = listOf(
                navArgument("parentId") {
                    type = NavType.StringType
                }
            )
        ) {
            PersonListScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                snackbarHostState = snackbarHostState,
                imageLoader = imageLoader
            )
        }
    }
}