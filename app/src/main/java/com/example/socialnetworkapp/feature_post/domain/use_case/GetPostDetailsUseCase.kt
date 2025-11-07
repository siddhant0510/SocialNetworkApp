package com.example.socialnetworkapp.feature_post.domain.use_case

import com.example.socialnetworkapp.domain.models.Post
import com.example.socialnetworkapp.feature_post.domain.repository.PostRepository
import com.example.socialnetworkapp.utli.Resource

class GetPostDetailsUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(postId: String): Resource<Post> {
        return repository.getPostDetails(postId)
    }
}