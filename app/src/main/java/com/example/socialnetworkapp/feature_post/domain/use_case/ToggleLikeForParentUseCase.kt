package com.example.socialnetworkapp.feature_post.domain.use_case

import com.example.socialnetworkapp.feature_post.domain.repository.PostRepository
import com.example.socialnetworkapp.utli.SimpleResource

class ToggleLikeForParentUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        parentId: String,
        parentType: Int,
        isLiked: Boolean
    ): SimpleResource {
        return if (isLiked) {
            repository.unlikeParent(parentId, parentType)
        } else {
            repository.likeParent(parentId, parentType)
        }
    }
}