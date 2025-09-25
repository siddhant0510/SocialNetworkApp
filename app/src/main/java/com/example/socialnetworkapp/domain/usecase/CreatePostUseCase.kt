package com.example.socialnetworkapp.domain.usecase

import android.net.Uri
import com.example.socialnetworkapp.domain.repository.PostRepository
import com.example.socialnetworkapp.utli.SimpleResource

class CreatePostUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(
        description: String,
        imageUri: Uri
    ): SimpleResource {
        return repository.createPost(description, imageUri)
    }
}