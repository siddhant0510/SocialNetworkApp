package com.example.socialnetworkapp.presentation.person_list

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
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
import androidx.navigation.NavController
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.models.User
import com.example.socialnetworkapp.presentation.componenets.StandardTextField
import com.example.socialnetworkapp.presentation.componenets.StandardToolbar
import com.example.socialnetworkapp.presentation.componenets.UserProfileItem
import com.example.socialnetworkapp.presentation.ui.theme.SpaceLarge
import com.example.socialnetworkapp.presentation.ui.theme.SpaceMedium
import com.example.socialnetworkapp.presentation.ui.theme.SpaceSmall
import com.example.socialnetworkapp.presentation.util.states.StandardTextFieldState

@Composable
fun PersonListScreen(
    navController: NavController
){
    Column(
        modifier = Modifier.fillMaxSize().padding(top = 48.dp)
    ){
        StandardToolbar(
            navController = navController,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(id = R.string.liked_by),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(SpaceLarge)
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