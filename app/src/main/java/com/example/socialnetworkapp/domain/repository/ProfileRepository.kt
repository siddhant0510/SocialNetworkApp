package com.example.socialnetworkapp.domain.repository

import android.net.Uri
import com.example.socialnetworkapp.domain.models.Profile
import com.example.socialnetworkapp.domain.models.Skill
import com.example.socialnetworkapp.presentation.edit_profile.request.UpdateProfileData
import com.example.socialnetworkapp.utli.Resource
import com.example.socialnetworkapp.utli.SimpleResource

interface ProfileRepository {

    suspend fun getProfile(userId: String): Resource<Profile>

    suspend fun updateProfile(
        updateProfileData: UpdateProfileData,
        bannerImageUri: Uri?,
        profilePictureUri: Uri?
    ): SimpleResource

    suspend fun getSkills(): Resource<List<Skill>>
}