package com.example.socialnetworkapp.presentation.feature_activity.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.models.Activity
import com.example.socialnetworkapp.presentation.componenets.StandardToolbar
import com.example.socialnetworkapp.presentation.feature_activity.presentation.components.ActivityItem
import com.example.socialnetworkapp.presentation.ui.theme.SpaceMedium

@Composable
fun ActivityScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: ActivityViewModel = hiltViewModel()
){
    val state = viewModel.state.value
    val activities = viewModel.activities.collectAsLazyPagingItems()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 48.dp)
        ){
            StandardToolbar(
                onNavigateUp = onNavigateUp,
                title = {
                    Text(
                        text = stringResource(id = R.string.your_activity),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                showBackArrow = false,
            )
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(SpaceMedium)
            ){
                items(
                    count = activities.itemCount
                ){ index ->
                    val activity = activities[index]
                    activity?.let { activity->
                        ActivityItem(
                            activity = Activity(
                                activity.userId,
                                activityType = activity.activityType,
                                formatedTime = activity.formatedTime,
                                parentId = activity.parentId,
                                username = activity.username
                            ),
                            onNavigate = onNavigate
                        )
                    }
                }
            }
        }
        if(state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}