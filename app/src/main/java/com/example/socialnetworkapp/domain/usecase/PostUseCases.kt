package com.example.socialnetworkapp.domain.usecase

data class PostUseCases(
    val getPostForFollowsUseCase: GetPostForFollowsUseCase,
    val createPostUseCase: CreatePostUseCase
)