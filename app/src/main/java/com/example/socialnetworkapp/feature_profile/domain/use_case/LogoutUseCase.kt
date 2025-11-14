package com.example.socialnetworkapp.feature_profile.domain.use_case

import com.example.socialnetworkapp.feature_profile.domain.repository.ProfileRepository

class LogoutUseCase(
    private val repository: ProfileRepository
) {

    operator fun invoke() {
        repository.logout()
    }
}