package com.example.socialnetworkapp.feature_profile.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.models.User
import com.example.socialnetworkapp.presentation.componenets.StandardTextField
import com.example.socialnetworkapp.presentation.componenets.StandardToolbar
import com.example.socialnetworkapp.presentation.componenets.UserProfileItem
import com.example.socialnetworkapp.theme.SpaceLarge
import com.example.socialnetworkapp.theme.SpaceMedium
import com.example.socialnetworkapp.utli.Screen

@Composable
fun SearchScreen(
    imageLoader: ImageLoader,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: SearchViewModel = hiltViewModel()
){
    val state = viewModel.searchState.value
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 48.dp)
        ){
            StandardToolbar(
                onNavigateUp = onNavigateUp,
                showBackArrow = true,
                title = {
                    Text(
                        text = stringResource(id = R.string.search_for_users),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceLarge)
            ){
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.searchFieldState.value.text,
                    hint = stringResource(id = R.string.search),
                    error = "",
                    singleLine = false,
                    leadingIcon = Icons.Default.Search,
                    onValueChange = {
                        viewModel.onEvent(SearchEvent.Query(it))
                    },
                )
                Spacer(modifier = Modifier.Companion.height(SpaceLarge))
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ){
                    items(state.userItems) { user ->
                        UserProfileItem(
                            user = user,
                            imageLoader = imageLoader,
                            actionIcon = {
                                if(user.isFollowing) {
                                    IconButton(
                                        onClick = {
                                            viewModel.onEvent(SearchEvent.ToggleFollowState(user.userId))
                                        }
                                    ) {
                                        Icon(
                                            imageVector = if(user.isFollowing) {
                                                Icons.Default.PersonRemove
                                            } else Icons.Default.PersonAdd,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                }
                            },
                            onItemClick = {
                                onNavigate(
                                    Screen.ProfileScreen.route + "?userId=${user.userId}"
                                )
                            }
                        )
                        Spacer(modifier = Modifier.Companion.height(SpaceMedium))
                    }

                }
            }
        }
        if(state.isLoading) (
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
        )
    }
}
















