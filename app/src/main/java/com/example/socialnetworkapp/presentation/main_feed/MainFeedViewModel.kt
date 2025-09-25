package com.example.socialnetworkapp.presentation.main_feed

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.socialnetworkapp.domain.usecase.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainFeedViewModel @Inject constructor(
    private val postUseCases: PostUseCases
) : ViewModel() {

    private val _state = mutableStateOf(MainFeedState())
    val state: State<MainFeedState> = _state

    val posts = postUseCases.getPostForFollowsUseCase()
        .cachedIn(viewModelScope)

    fun onEvent(event: MainFeedEvent) {
        when(event) {
            is MainFeedEvent.LoadMorePosts -> {
                _state.value = state.value.copy(
                    isLoadingNewPosts = true
                )
            }
            is MainFeedEvent.LoadedPage -> {
                _state.value = state.value.copy(
                    isLoadingFirstTime = false,
                    isLoadingNewPosts = false
                )
            }
        }
    }
}
