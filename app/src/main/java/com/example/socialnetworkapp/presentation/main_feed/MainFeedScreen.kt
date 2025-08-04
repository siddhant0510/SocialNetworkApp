package com.example.socialnetworkapp.presentation.main_feed

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.presentation.componenets.Post
import com.example.socialnetworkapp.presentation.componenets.StandardToolbar
import com.example.socialnetworkapp.presentation.util.Screen

// import com.example.socialnetworkapp.presentation.componenets.StandardScaffold

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainFeedScreen(
    navController: NavController
){
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 48.dp)
    ){
        StandardToolbar(
            navController = navController,
            title = {
                Text(
                    text = stringResource(id = R.string.your_feed),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true,
            navActions = {
                IconButton(onClick = {
                    navController.navigate(Screen.SearchScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        )
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
            ),
            onPostClick = {
                navController.navigate(Screen.PostDetailsScreen.route)
            }
        )
    }
}