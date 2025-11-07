package com.example.socialnetworkapp.feature_post.presentation.main_feed

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.presentation.componenets.Post
import com.example.socialnetworkapp.presentation.componenets.StandardToolbar
import com.example.socialnetworkapp.utli.Screen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainFeedScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    scaffoldState: SnackbarHostState,
    viewModel: MainFeedViewModel = hiltViewModel()
){
    val posts = viewModel.posts.collectAsLazyPagingItems()
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
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
            if(state.isLoadingFirstTime) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            LazyColumn {
                items(
                    count = posts.itemCount,
                    key = { index -> posts[index]?.hashCode() ?: index }
                ) {
                        index ->
                    val post = posts[index]
                    if (post != null) {
                        Post(
                            post = com.example.socialnetworkapp.domain.models.Post(
                                id = post?.id ?: "",
                                username = post.username ?: "",
                                imageUrl = post.imageUrl ?: "",
                                profilePictureUrl = post.profilePictureUrl ?: "",
                                description = post.description ?: "",
                                likeCount = post.likeCount ?: 0,
                                commentCount = post.commentCount ?: 0
                            ),
                            onPostClick = {
                                onNavigate(Screen.PostDetailsScreen.route + "/${post?.id}")
                            }
                        )
                    }
                }
                item {
                    if(state.isLoadingNewPosts) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.BottomCenter))
                    }
                }
                posts.apply {
                    when {
                        LazyPagingItems.loadState.refresh !is LoadState.Loading -> {
                            viewModel.onEvent(MainFeedEvent.LoadedPage)
                        }
                        LazyPagingItems.loadState.append is LoadState.Loading -> {
                            viewModel.onEvent(MainFeedEvent.LoadMorePosts)
                        }
                        LazyPagingItems.loadState.append is LoadState.Error -> {
                            scope.launch {
                                scaffoldState.showSnackbar(
                                    message = "Error"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}