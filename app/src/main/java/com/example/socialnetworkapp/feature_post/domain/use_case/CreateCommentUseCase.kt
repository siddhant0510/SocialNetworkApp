package com.example.socialnetworkapp.feature_post.domain.use_case

import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.feature_post.domain.repository.PostRepository
import com.example.socialnetworkapp.utli.Resource
import com.example.socialnetworkapp.utli.SimpleResource
import com.example.socialnetworkapp.utli.UiText

class CreateCommentUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(postId: String, comment: String): SimpleResource {
        if(comment.isBlank()) {
            return Resource.Error(UiText.StringResource(R.string.error_field_empty))
        }
        if(postId.isBlank()) {
            return Resource.Error(UiText.unknownError())
        }
        return repository.createComment(postId, comment)
    }
}