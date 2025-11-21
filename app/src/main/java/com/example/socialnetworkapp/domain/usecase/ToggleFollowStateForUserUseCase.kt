package com.example.socialnetworkapp.domain.usecase

import com.example.socialnetworkapp.domain.repository.ProfileRepository
import com.example.socialnetworkapp.utli.SimpleResource

class ToggleFollowStateForUserUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(userId: String, isFollowing: Boolean): SimpleResource {
        return if(isFollowing) {
            repository.unfollowUser(userId)
        } else {
            repository.followUser(userId)
        }
    }
}