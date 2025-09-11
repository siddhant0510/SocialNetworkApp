package com.example.socialnetworkapp.presentation.search

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.models.User
import com.example.socialnetworkapp.presentation.componenets.StandardTextField
import com.example.socialnetworkapp.presentation.componenets.StandardToolbar
import com.example.socialnetworkapp.presentation.componenets.UserProfileItem
import com.example.socialnetworkapp.presentation.ui.theme.SpaceLarge
import com.example.socialnetworkapp.presentation.ui.theme.SpaceMedium
import com.example.socialnetworkapp.presentation.ui.theme.iconSizeMedium
import com.example.socialnetworkapp.presentation.util.EditProfileError
import com.example.socialnetworkapp.presentation.util.states.StandardTextFieldState

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
){
    Column(
        modifier = Modifier.fillMaxSize().padding(top = 48.dp)
    ){
        StandardToolbar(
            navController = navController,
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
                text = viewModel.searchState.value.text,
                hint = stringResource(id = R.string.search),
                error = "",
                singleLine = false,
                leadingIcon = Icons.Default.Search,
                onValueChange = {
                    viewModel.setSearchState(
                        StandardTextFieldState(text = it)
                    )
                },
            )
            Spacer(modifier = Modifier.height(SpaceLarge))
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ){
                items(10) {
                    UserProfileItem(
                        user = User(
                            profilePictureUrl = "",
                            username = "Siddhant Kudale",
                            description = "This is description This is " +
                                    "descriptionThis is descriptionThis is" +
                                    " descriptionThis is descriptionThis is" +
                                    " descriptionThis is descriptionThis is",
                            followerCount = 234,
                            followingCount = 223,
                            postCount = 12
                        ),
                        actionIcon = {
                            Icon(
                                imageVector = Icons.Default.PersonAdd,
                                contentDescription = null
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(SpaceMedium))
                }

            }
        }
    }
}
















