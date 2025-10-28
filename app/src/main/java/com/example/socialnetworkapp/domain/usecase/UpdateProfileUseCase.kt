package com.example.socialnetworkapp.domain.usecase

import android.net.Uri
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.repository.ProfileRepository
import com.example.socialnetworkapp.presentation.edit_profile.request.UpdateProfileData
import com.example.socialnetworkapp.utli.Resource
import com.example.socialnetworkapp.utli.SimpleResource
import com.example.socialnetworkapp.utli.UiText

class UpdateProfileUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(
        updateProfileData: UpdateProfileData,
        profilePictureUri: Uri?,
        bannerUri: Uri?
    ): SimpleResource {
        if(updateProfileData.username.isBlank()) {
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_username_empty)
            )
        }
        return repository.updateProfile(
            updateProfileData = updateProfileData,
            profilePictureUri = profilePictureUri,
            bannerImageUri = bannerUri
        )
    }
}