package com.example.socialnetworkapp.feature_post.domain.use_case

import com.example.socialnetworkapp.domain.models.UserItem
import com.example.socialnetworkapp.feature_post.domain.repository.PostRepository
import com.example.socialnetworkapp.utli.Resource

class GetLikesForParentUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(parentId: String): Resource<List<UserItem>> {
        return repository.getLikesForPostParent(parentId)
    }
}