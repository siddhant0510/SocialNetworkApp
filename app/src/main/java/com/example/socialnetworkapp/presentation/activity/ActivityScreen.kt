package com.example.socialnetworkapp.presentation.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.models.Activity
import com.example.socialnetworkapp.domain.util.ActivityAction
import com.example.socialnetworkapp.domain.util.DateFormatUtil
import com.example.socialnetworkapp.presentation.componenets.StandardToolbar
import com.example.socialnetworkapp.presentation.ui.theme.SpaceMedium
import kotlin.random.Random

//import com.example.socialnetworkapp.presentation.componenets.StandardScaffold

@Composable
fun ActivityScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: ActivityViewModel = hiltViewModel()
){
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
            items(20){
                ActivityItem(
                    activity = Activity(
                        "Siddhant Kudale",
                        actionType = if(Random.nextInt(2) == 0){
                            ActivityAction.LikedPost
                        }else{
                            ActivityAction.CommentedOnPost
                        },
                        formatedTime = DateFormatUtil.timestampToFormattedString(
                            timestamp = System.currentTimeMillis(),
                            pattern = "MMM dd, HH:mm"
                        )
                    ),
                )
                if(it < 19){
                    Spacer(modifier = Modifier.height(SpaceMedium))
                }
            }
        }
    }
}