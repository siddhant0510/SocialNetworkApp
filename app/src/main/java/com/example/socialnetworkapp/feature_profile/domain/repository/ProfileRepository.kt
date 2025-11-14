package com.example.socialnetworkapp.feature_profile.domain.repository

import android.net.Uri
import com.example.socialnetworkapp.domain.models.Post
import com.example.socialnetworkapp.domain.models.UserItem
import com.example.socialnetworkapp.feature_profile.domain.model.Profile
import com.example.socialnetworkapp.feature_profile.domain.model.Skill
import com.example.socialnetworkapp.feature_profile.domain.model.UpdateProfileData
import com.example.socialnetworkapp.utli.Constants
import com.example.socialnetworkapp.utli.Resource
import com.example.socialnetworkapp.utli.SimpleResource

interface ProfileRepository {

    suspend fun getPostsPaged(
        page: Int = 0,
        pageSize: Int = Constants.DEFAULT_PAGE_SIZE,
        userId: String
    ): Resource<List<Post>>

    suspend fun getProfile(userId: String): Resource<Profile>

    suspend fun updateProfile(
        updateProfileData: UpdateProfileData,
        bannerImageUri: Uri?,
        profilePictureUri: Uri?
    ): SimpleResource

    suspend fun getSkills(): Resource<List<Skill>>

    suspend fun searchUser(query: String): Resource<List<UserItem>>

    suspend fun followUser(userId: String): SimpleResource

    suspend fun unfollowUser(userId: String): SimpleResource

    fun logout()
}