package com.example.socialnetworkapp.feature_post.presentation.main_feed

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.feature_post.presentation.person_list.PostEvent
import com.example.socialnetworkapp.presentation.componenets.Post
import com.example.socialnetworkapp.presentation.componenets.StandardToolbar
import com.example.socialnetworkapp.presentation.theme.SpaceLarge
import com.example.socialnetworkapp.utli.Screen
import com.example.socialnetworkapp.utli.sendSharePostIntent
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainFeedScreen(
    imageLoader: ImageLoader,
    snackbarHostState: SnackbarHostState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: MainFeedViewModel = hiltViewModel()
){
    val pagingState = viewModel.pagingState.value
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is PostEvent.OnLiked -> {

                }
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 48.dp)
    ){
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = stringResource(id = R.string.your_feed),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false,
            navActions = {
                IconButton(onClick = {
                    onNavigate(Screen.SearchScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        )
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn {
                items(pagingState.items.size) { i ->
                    val post = pagingState.items[i]
                    if(i >= pagingState.items.size - 1 && !pagingState.endReached && !pagingState.isLoading) {
                        viewModel.loadNextPosts()
                    }
                    Post(
                        post = post,
                        imageLoader = imageLoader,
                        onUsernameClick = {
                            onNavigate(Screen.PostDetailsScreen.route + "/${post.id}")
                        },
                        onPostClick = {
                            onNavigate(Screen.PostDetailsScreen.route + "/${post.id}")
                        },
                        onCommentClick = {
                            onNavigate(Screen.PostDetailsScreen.route + "/${post.id}?shouldShowKeyboard=true")
                        },
                        onLikeClick = {
                            viewModel.onEvent(MainFeedEvent.LikedPost(post.id))
                        },
                        onShareClick = {
                            context.sendSharePostIntent(post.id)
                        },
                        snackbarHostState = snackbarHostState
                    )
                    if(i < pagingState.items.size - 1) {
                        Spacer(modifier = Modifier.height(SpaceLarge))
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(90.dp))
                }
            }
            if(pagingState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}