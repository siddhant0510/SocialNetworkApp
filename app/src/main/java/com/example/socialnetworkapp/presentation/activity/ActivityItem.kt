package com.example.socialnetworkapp.presentation.activity

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.models.Activity
import com.example.socialnetworkapp.domain.util.ActivityAction
import com.example.socialnetworkapp.presentation.ui.theme.SpaceSmall
import javax.annotation.meta.When

@Composable
fun ActivityItem(
    activity: Activity,
    modifier: Modifier = Modifier,
){
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceSmall),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            val fillerText = when(activity.actionType){
                is ActivityAction.LikedPost ->
                    stringResource(id = R.string.liked)
                is ActivityAction.CommentedOnPost ->
                    stringResource(id = R.string.commented_on)
                is ActivityAction.FollowedYou ->
                    stringResource(id = R.string.followed_you)
            }
            val actionText = when(activity.actionType){
                is ActivityAction.LikedPost ->
                    stringResource(id = R.string.your_post)
                is ActivityAction.CommentedOnPost ->
                    stringResource(id = R.string.your_post)
                is ActivityAction.FollowedYou -> ""
            }
            Text(
                text = buildAnnotatedString {
                    val bodyStyle = SpanStyle(fontWeight = FontWeight.Bold)
                    withStyle(bodyStyle){
                        append(activity.username)
                    }
                    append(" $fillerText ")
                    withStyle(bodyStyle){
                        append(actionText)
                    }
                },
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = activity.formatedTime,
                textAlign = TextAlign.Right,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}