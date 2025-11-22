package com.example.socialnetworkapp.feature_chat.presentation.message

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.feature_chat.domain.model.Message
import com.example.socialnetworkapp.feature_chat.presentation.message.components.OwnMessage
import com.example.socialnetworkapp.feature_chat.presentation.message.components.RemoteMessage
import com.example.socialnetworkapp.presentation.componenets.SendTextField
import com.example.socialnetworkapp.presentation.componenets.StandardToolbar
import com.example.socialnetworkapp.presentation.theme.ProfilePictureSizeSmall
import com.example.socialnetworkapp.presentation.theme.SpaceMedium

@Composable
fun MessageScreen(
    chatId: String,
    imageLoader: ImageLoader,
    onNavigateUp: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
    viewModel: MessageViewModel = hiltViewModel()
) {
    val messages = remember {
        listOf(
            Message(
                fromId = "",
                toId = "",
                text = "Hello World!",
                formattedTime = "19:34",
                chatId = ""
            ),
            Message(
                fromId = "",
                toId = "",
                text = "Hello World!",
                formattedTime = "19:34",
                chatId = ""
            )
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            title = {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = "",
                        imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(ProfilePictureSizeSmall)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(SpaceMedium))
                Text(
                    text = "Sid",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(SpaceMedium)
            ) {
                items(messages) { message ->
                    RemoteMessage(
                        message = message.text,
                        formatedTime = message.formattedTime,
                        color = MaterialTheme.colorScheme.surface,
                        textColor = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(SpaceMedium))
                    OwnMessage(
                        message = message.text,
                        formatedTime = message.formattedTime,
                        color = Color.White,
                        textColor = MaterialTheme.colorScheme.background
                    )
                    Spacer(modifier = Modifier.height(SpaceMedium))
                }
            }
            SendTextField(
                state = viewModel.messageTextFieldState.value,
                onValueChange = {
                    viewModel.onEvent(MessageEvent.EnterMessage(it))
                },
                onSend = {
                    viewModel.onEvent(MessageEvent.SendMessage)
                },
                hint = stringResource(id = R.string.enter_a_message)
            )
        }

    }
}
