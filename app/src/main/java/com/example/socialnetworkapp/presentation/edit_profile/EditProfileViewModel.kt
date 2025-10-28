package com.example.socialnetworkapp.presentation.edit_profile

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.usecase.ProfileUseCases
import com.example.socialnetworkapp.presentation.edit_profile.request.UpdateProfileData
import com.example.socialnetworkapp.presentation.profile.ProfileState
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
class EditProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    savedStateHandel: SavedStateHandle
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _usernameState = mutableStateOf(StandardTextFieldState())
    val usernameState: State<StandardTextFieldState> = _usernameState

    private val _githubTextFieldState = mutableStateOf(StandardTextFieldState())
    val githubTextFieldState: State<StandardTextFieldState> = _githubTextFieldState

    private val _instagramTextFieldState = mutableStateOf(StandardTextFieldState())
    val instagramTextFieldState: State<StandardTextFieldState> = _instagramTextFieldState

    private val _linkedInTextFieldState = mutableStateOf(StandardTextFieldState())
    val linkedInTextFiledState: State<StandardTextFieldState> = _linkedInTextFieldState

    private val _bioState = mutableStateOf(StandardTextFieldState())
    val bioState: State<StandardTextFieldState> = _bioState

    private val _skills = mutableStateOf(SkillsState())
    val skills: State<SkillsState> = _skills

    private val _profileState = mutableStateOf(ProfileState())
    val profileState: State<ProfileState> = _profileState

    private val _bannerUri = mutableStateOf<Uri?>(null)
    val bannerUri: State<Uri?> = _bannerUri

    private val _profilePictureUri = mutableStateOf<Uri?>(null)
    val profilePictureUri: State<Uri?> = _profilePictureUri

    init {
        savedStateHandel.get<String>("userId")?.let { userId ->
            getSkills()
            getProfile(userId)
        }
    }

    private fun getSkills() {
        viewModelScope.launch {
            val result = profileUseCases.getSkills()
            when(result) {
                is Resource.Success -> {
                    _skills.value = skills.value.copy(
                        skills = result.data ?: run {
                            _eventFlow.emit(UiEvent.ShowSnakbar(
                                UiText.StringResource(R.string.error_couldnt_load_skills)
                            ))
                            return@launch
                        }
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnakbar(result.uiText ?: UiText.unknownError()))
                    return@launch
                }
            }
        }
    }

    private fun getProfile(userId: String) {
        viewModelScope.launch {
            _profileState.value = profileState.value.copy(
                isLoading = true
            )
            val result = profileUseCases.getProfile(userId)
            when(result) {
                is Resource.Success -> {
                    val profile = result.data ?: run {
                        _eventFlow.emit(UiEvent.ShowSnakbar(
                            UiText.StringResource(R.string.error_couldnt_load_profile)
                        ))
                        return@launch
                    }
                    _usernameState.value = usernameState.value.copy(
                        text = profile.username
                    )
                    _githubTextFieldState.value = _githubTextFieldState.value.copy(
                        text = profile.gitHubUrl ?: ""
                    )
                    _instagramTextFieldState.value = _instagramTextFieldState.value.copy(
                        text = profile.instagramUrl ?: ""
                    )
                    _linkedInTextFieldState.value = _linkedInTextFieldState.value.copy(
                        text = profile.linkedInUrl ?: ""
                    )
                    _bioState.value = bioState.value.copy(
                        text = profile.bio
                    )
                    _skills.value = _skills.value.copy(
                        selectedSkills = profile.topSkills
                    )
                    _profileState.value = profileState.value.copy(
                        profile = profile,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnakbar(result.uiText ?: UiText.unknownError()))
                    return@launch
                }
            }
        }
    }

    private fun updateProfile() {
        viewModelScope.launch {
            val result = profileUseCases.updateProfile(
                updateProfileData = UpdateProfileData(
                    username = usernameState.value.text,
                    bio = bioState.value.text,
                    gitHubUrl = githubTextFieldState.value.text,
                    instagramUrl = instagramTextFieldState.value.text,
                    linkedInUrl = linkedInTextFiledState.value.text,
                    skills = skills.value.selectedSkills
                ),
                profilePictureUri = profilePictureUri.value,
                bannerUri = bannerUri.value
            )
            when(result) {
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.ShowSnakbar(UiText.StringResource(R.string.successfully_update_profile)))
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnakbar(result.uiText ?: UiText.unknownError()))
                }
            }
        }
    }

    fun onEvent(event: EditProfileEvent) {
        when(event) {
            is EditProfileEvent.EnteredUsername -> {
                _usernameState.value = usernameState.value.copy(
                    text = event.value
                )
            }
            is EditProfileEvent.EnteredGitHubUrl -> {
                _githubTextFieldState.value = githubTextFieldState.value.copy(
                    text = event.value
                )
            }
            is EditProfileEvent.EnteredInstagramUrl -> {
                _instagramTextFieldState.value = instagramTextFieldState.value.copy(
                    text = event.value
                )
            }
            is EditProfileEvent.EnteredLinkedInUrl -> {
                _linkedInTextFieldState.value = linkedInTextFiledState.value.copy(
                    text = event.value
                )
            }
            is EditProfileEvent.EnteredBio -> {
                _bioState.value = bioState.value.copy(
                    text = event.value
                )
            }
            is EditProfileEvent.CropProfilePicture -> {
                _profilePictureUri.value = event.uri
            }
            is EditProfileEvent.CropBannerImage -> {
                _bannerUri.value = event.uri
            }
            is EditProfileEvent.SetSkillsSelected -> {

            }
            EditProfileEvent.UpdateProfile -> {

            }
        }
    }
}