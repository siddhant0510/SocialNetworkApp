package com.example.socialnetworkapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.example.socialnetworkapp.presentation.componenets.Navigation
import com.example.socialnetworkapp.presentation.components.StandardScaffold
import com.example.socialnetworkapp.theme.SocialNetworkAppTheme
import com.example.socialnetworkapp.utli.Screen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SocialNetworkAppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.Companion.fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val snackbarHostState = remember { SnackbarHostState() }
                    StandardScaffold(
                        navController = navController,
                        showBottomBar = shouldShowBottomBar(navBackStackEntry),
                        state = snackbarHostState,
                        modifier = Modifier.Companion.fillMaxSize(),
                        onFabClick = {
                            navController.navigate(Screen.CreatePostScreen.route)
                        }
                    ) {
                        Navigation(navController, snackbarHostState, imageLoader)
                    }
                }
            }
        }
    }
    private fun shouldShowBottomBar(backStackEntry: NavBackStackEntry?): Boolean {
        val route = backStackEntry?.destination?.route
        val doesRouteMatch = listOf(
            Screen.MainFeedScreen.route,
            Screen.ChatScreen.route,
            Screen.ActivityScreen.route
        ).any { it == route }

        val isOwnProfile = route == "${Screen.ProfileScreen.route}?userId={userId}" &&
                backStackEntry?.arguments?.getString("userId") == null
        return doesRouteMatch || isOwnProfile
    }
}