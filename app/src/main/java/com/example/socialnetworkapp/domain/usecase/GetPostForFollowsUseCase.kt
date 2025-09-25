package com.example.socialnetworkapp.domain.usecase

import androidx.paging.PagingData
import com.example.socialnetworkapp.domain.models.Post
import com.example.socialnetworkapp.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostForFollowsUseCase(
    private val repository: PostRepository
) {
    operator fun invoke(): Flow<PagingData<Post>> {
        return repository.posts
    }
}