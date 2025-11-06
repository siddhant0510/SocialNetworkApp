package com.example.socialnetworkapp.presentation.feature_profile.domain.use_case

import com.example.socialnetworkapp.presentation.feature_profile.domain.model.Profile
import com.example.socialnetworkapp.presentation.feature_profile.domain.repository.ProfileRepository
import com.example.socialnetworkapp.utli.Resource

class GetProfileUseCase (
    private val repository: ProfileRepository
){
    suspend operator fun invoke(userId: String): Resource<Profile> {
        return repository.getProfile(userId)
    }
}