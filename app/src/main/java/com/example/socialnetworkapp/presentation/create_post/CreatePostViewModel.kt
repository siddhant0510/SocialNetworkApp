package com.example.socialnetworkapp.presentation.create_post

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.usecase.PostUseCases
import com.example.socialnetworkapp.presentation.util.UiEvent
import com.example.socialnetworkapp.presentation.util.states.StandardTextFieldState
import com.example.socialnetworkapp.utli.Resource
import com.example.socialnetworkapp.utli.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val postUseCases: PostUseCases
) : ViewModel() {

    private val _descriptionState = mutableStateOf(StandardTextFieldState())
    val descriptionState: State<StandardTextFieldState> = _descriptionState
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    private val _chosenImageUri = mutableStateOf<Uri?>(null)
    val chosenImageUri: State<Uri?> = _chosenImageUri

    fun onEvent(event: CreatePostEvent) {
        when(event) {
            is CreatePostEvent.EnterDescription -> {
                _descriptionState.value = descriptionState.value.copy(
                    text = event.value
                )
            }
            is CreatePostEvent.CropImage -> {
                _chosenImageUri.value = event.uri
            }
            is CreatePostEvent.PickImage -> {
                _chosenImageUri.value = event.uri
            }
            is CreatePostEvent.PostImage -> {
                viewModelScope.launch {
                    _isLoading.value = true
                    val result = postUseCases.createPostUseCase(
                        description = descriptionState.value.text,
                        imageUri = chosenImageUri.value
                    )
                    when(result) {
                        is Resource.Success -> {
                            _eventFlow.emit(UiEvent.ShowSnakbar(
                                uiText = UiText.StringResource(R.string.post_created)
                            ))
                            _eventFlow.emit((UiEvent.NavigateUp))
                        }
                        is Resource.Error -> {
                            _eventFlow.emit(UiEvent.ShowSnakbar(
                                result.uiText ?: UiText.unknownError()
                            ))
                        }
                    }
                    _isLoading.value = false
                }
            }

        }
    }
}