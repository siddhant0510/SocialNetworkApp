package com.example.socialnetworkapp.domain.usecase

import com.example.socialnetworkapp.domain.models.Profile
import com.example.socialnetworkapp.domain.repository.ProfileRepository
import com.example.socialnetworkapp.utli.Resource

class GetProfileUseCase (
    private val repository: ProfileRepository
){
    suspend operator fun invoke(userId: String): Resource<Profile> {
        return repository.getProfile(userId)
    }
}