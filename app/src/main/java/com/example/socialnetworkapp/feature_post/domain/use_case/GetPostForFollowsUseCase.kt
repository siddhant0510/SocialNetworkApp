package com.example.socialnetworkapp.feature_post.domain.use_case

import com.example.socialnetworkapp.domain.models.Post
import com.example.socialnetworkapp.feature_post.domain.repository.PostRepository
import com.example.socialnetworkapp.utli.Constants
import com.example.socialnetworkapp.utli.Resource

class GetPostForFollowsUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(
        page: Int,
        pageSize: Int = Constants.DEFAULT_PAGE_SIZE
    ): Resource<List<Post>> {
        return repository.getPostsForFollows(page, pageSize)
    }
}