package com.example.socialnetworkapp.feature_profile.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialnetworkapp.domain.models.Post
import com.example.socialnetworkapp.domain.usecase.GetOwnUserIdUseCase
import com.example.socialnetworkapp.feature_post.domain.use_case.PostUseCases
import com.example.socialnetworkapp.feature_profile.domain.use_case.ProfileUseCases
import com.example.socialnetworkapp.presentation.PagingState
import com.example.socialnetworkapp.utilNew.UiEvent
import com.example.socialnetworkapp.utli.DefaultPaginator
import com.example.socialnetworkapp.utli.Event
import com.example.socialnetworkapp.utli.ParentType
import com.example.socialnetworkapp.utli.PostLiker
import com.example.socialnetworkapp.utli.Resource
import com.example.socialnetworkapp.utli.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val postUseCases: PostUseCases,
    private val getOwnUserId: GetOwnUserIdUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val postLiker: PostLiker
) : ViewModel() {

    private val _toolbarState = mutableStateOf(ProfileToolbarState())
    val toolbarState: State<ProfileToolbarState> = _toolbarState

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _pagingState = mutableStateOf<PagingState<Post>>(PagingState())
    val pagingState: State<PagingState<Post>> = _pagingState

    private val paginator = DefaultPaginator(
        onLoadUpdated = { isLoading ->
            _pagingState.value = pagingState.value.copy(
                isLoading = isLoading
            )
        },
        onRequest = { page ->
            val userId = savedStateHandle.get<String>("userId") ?: getOwnUserId()
            profileUseCases.getPostsForProfile(
                userId = userId,
                page = page
            )
        },
        onSuccess = { posts ->
            _pagingState.value = pagingState.value.copy(
                items = pagingState.value.items + posts,
                endReached = posts.isEmpty(),
                isLoading = false
            )
        },
        onError = { uiText ->
            _eventFlow.emit(UiEvent.ShowSnakbar(uiText = uiText))
        }
    )

    fun setExpandedRatio(ratio: Float){
        _toolbarState.value = _toolbarState.value.copy(expandedRatio = ratio)
    }

    fun setToolbarOffsetY(value: Float){
        _toolbarState.value = _toolbarState.value.copy(toolbarOffsetY = value)
    }

    init {
        loadNextPosts()
    }

    fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.GetProfile -> {

            }
            is ProfileEvent.LikedPost -> {
                viewModelScope.launch {
                    toggleLikeForParent(
                        parentId = event.postId
                    )
                }
            }
        }
    }

    private fun toggleLikeForParent(
        parentId: String
    ) {
        viewModelScope.launch {
            postLiker.toggleLike(
                posts = pagingState.value.items,
                parentId = parentId,
                onRequest = { isLiked ->
                    postUseCases.toggleLikeForParent(
                        parentId = parentId,
                        parentType = ParentType.Post.type,
                        isLiked = isLiked
                    )
                },
                onStateUpdated = { posts ->
                    _pagingState.value = pagingState.value.copy(
                        items = posts
                    )
                }
            )
        }
    }

     fun getProfile(userId: String?) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true
            )
            val result = profileUseCases.getProfile(
                userId ?: getOwnUserId()
            )
            when(result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        profile = result.data,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnakbar(
                        uiText = result.uiText ?: UiText.unknownError()
                    ))
                    _state.value = state.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    fun loadNextPosts() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }
}
