package com.example.socialnetworkapp.feature_profile.domain.use_case

import com.example.socialnetworkapp.domain.models.Post
import com.example.socialnetworkapp.domain.repository.ProfileRepository
import com.example.socialnetworkapp.utli.Resource

class GetPostForProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(userId: String, page: Int): Resource<List<Post>> {
        return repository.getPostsPaged(
            userId = userId,
            page = page
        )
    }
}