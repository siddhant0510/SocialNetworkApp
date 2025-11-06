package com.example.socialnetworkapp.presentation.feature_post.domain.use_case

import android.net.Uri
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.presentation.feature_post.domain.repository.PostRepository
import com.example.socialnetworkapp.utli.Resource
import com.example.socialnetworkapp.utli.SimpleResource
import com.example.socialnetworkapp.utli.UiText

class CreatePostUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(
        description: String,
        imageUri: Uri?
    ): SimpleResource {
        if(imageUri == null) {
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_no_image_picked)
            )
        }
        if(description.isBlank()) {
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_description_blank)
            )
        }
        return repository.createPost(description, imageUri)
    }
}