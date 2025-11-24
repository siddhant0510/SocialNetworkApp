package com.example.socialnetworkapp.feature_chat.presentation.message

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialnetworkapp.presentation.util.UiEvent
import com.example.socialnetworkapp.domain.state.StandardTextFieldState
import com.example.socialnetworkapp.feature_chat.domain.model.Message
import com.example.socialnetworkapp.feature_chat.domain.use_case.ChatUseCases
import com.example.socialnetworkapp.presentation.PagingState
import com.example.socialnetworkapp.utli.DefaultPaginator
import com.example.socialnetworkapp.utli.Resource
import com.example.socialnetworkapp.utli.UiText
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _messageTextFieldState = mutableStateOf(StandardTextFieldState())
    val messageTextFieldState: State<StandardTextFieldState> = _messageTextFieldState

    private val _state = mutableStateOf(MessageState())
    val state: State<MessageState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _pagingState = mutableStateOf<PagingState<Message>>(PagingState())
    val pagingState: State<PagingState<Message>> = _pagingState

    private val paginator = DefaultPaginator(
        onLoadUpdated = { isLoading ->
            _pagingState.value = pagingState.value.copy(isLoading = isLoading)
        },
        onRequest = { nextPage ->
            savedStateHandle.get<String>("chatId")?.let { chatId ->
                chatUseCases.getMessagesForChat(
                    chatId, nextPage
                )
            } ?: Resource.Error(UiText.unknownError())
        },
        onError = { uiText ->
            _eventFlow.emit(UiEvent.ShowSnakbar(uiText = uiText))
        },
        onSuccess = { messages ->
            _pagingState.value = pagingState.value.copy(
                items = pagingState.value.items + messages,
                endReached = messages.isEmpty(),
                isLoading = false
            )
        }
    )

    init {
        loadNextMessages()
        observeChatEvents()
        observeChatMessage()
    }

    private fun observeChatMessage() {
        chatUseCases.observeMessages()
            .onEach { message ->
                _state.value = state.value.copy(
                    messages = state.value.messages + message
                )
            }.launchIn(viewModelScope)
    }

    private fun observeChatEvents() {
        chatUseCases.observeChatEvents()
            .onEach { event ->
                when(event) {
                    is WebSocket.Event.OnConnectionOpened<*> -> {

                    }

                    else -> Unit
                }
            }.launchIn(viewModelScope)
    }

    fun loadNextMessages() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun sendMessage() {
        val toId = savedStateHandle.get<String>("rememberUserId") ?: return
        if(messageTextFieldState.value.text.isBlank()) {
            return
        }
        val chatId = savedStateHandle.get<String>("chatId")
        chatUseCases.sendMessage(toId, messageTextFieldState.value.text, chatId)
    }

    fun onEvent(event: MessageEvent) {
        when(event) {
            is MessageEvent.EnterMessage -> {
                _messageTextFieldState.value = messageTextFieldState.value.copy(
                    text = event.message
                )
            }
            is MessageEvent.SendMessage -> {
                sendMessage()
            }
        }
    }
}