package com.example.socialnetworkapp.domain.usecase

import com.example.socialnetworkapp.domain.models.Post
import com.example.socialnetworkapp.domain.repository.PostRepository
import com.example.socialnetworkapp.utli.Resource

class GetPostForFollowsUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(
        page: Int,
        pageSize: Int
    ): Resource<List<Post>> {
        return repository.getPostsForFollows(page, pageSize)
    }
}