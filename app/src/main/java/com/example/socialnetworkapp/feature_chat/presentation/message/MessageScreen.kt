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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.feature_chat.presentation.message.components.OwnMessage
import com.example.socialnetworkapp.feature_chat.presentation.message.components.RemoteMessage
import com.example.socialnetworkapp.presentation.componenets.SendTextField
import com.example.socialnetworkapp.presentation.componenets.StandardToolbar
import com.example.socialnetworkapp.presentation.theme.ProfilePictureSizeSmall
import com.example.socialnetworkapp.presentation.theme.SpaceMedium
import okio.ByteString.Companion.decodeBase64
import java.nio.charset.Charset

@Composable
fun MessageScreen(
    remoteUserId: String,
    remoteUsername: String,
    encodedRemoteProfilePictureUrl: String,
    imageLoader: ImageLoader,
    onNavigateUp: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
    viewModel: MessageViewModel = hiltViewModel()
) {
    val decodedRemoteProfilePictureUrl = remember {
        encodedRemoteProfilePictureUrl.decodeBase64()?.string(Charset.defaultCharset())
    }
    val pagingState = viewModel.pagingState.value
    val state = viewModel.state.value
    val lazyListState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(key1 = pagingState, key2 = keyboardController) {
        viewModel.messageUpdatedEvent.collect { event ->
            when(event) {
                is MessageViewModel.MessageUpdateEvent.SingleMessageUpdate,
                is MessageViewModel.MessageUpdateEvent.MessagePageLoaded -> {
                    if(pagingState.items.isEmpty()) {
                        return@collect
                    }
                    lazyListState.scrollToItem(pagingState.items.size - 1)
                }
                is MessageViewModel.MessageUpdateEvent.MessageSent -> {
                    keyboardController?.hide()
                }
            }
            if(lazyListState.firstVisibleItemIndex == pagingState.items.size - 1) {
                lazyListState.scrollToItem(pagingState.items.size -1)
            }
        }
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
                        model = decodedRemoteProfilePictureUrl,
                        imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(ProfilePictureSizeSmall)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(SpaceMedium))
                Text(
                    text = remoteUsername,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .weight(1f)
                    .padding(SpaceMedium)
            ) {
                items(count = pagingState.items.size) { i ->
                    val message = pagingState.items[i]
                    if(i >= pagingState.items.size - 1 && !pagingState.endReached && !pagingState.isLoading) {
                        viewModel.loadNextMessages()
                    }
                    if(message.fromId == remoteUserId) {
                        RemoteMessage(
                            message = message.text,
                            formatedTime = message.formatedTime,
                            color = MaterialTheme.colorScheme.surface,
                            textColor = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(SpaceMedium))
                    } else {
                        OwnMessage(
                            message = message.text,
                            formatedTime = message.formatedTime,
                            color = Color.White,
                            textColor = MaterialTheme.colorScheme.background
                        )
                        Spacer(modifier = Modifier.height(SpaceMedium))
                    }
                }
            }
            SendTextField(
                state = viewModel.messageTextFieldState.value,
                canSendMessage = state.canSendMessage,
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
