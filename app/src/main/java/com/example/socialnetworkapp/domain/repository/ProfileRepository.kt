package com.example.socialnetworkapp.domain.repository

import com.example.socialnetworkapp.domain.models.Profile
import com.example.socialnetworkapp.utli.Resource

interface ProfileRepository {

    suspend fun getProfile(userId: String): Resource<Profile>
}