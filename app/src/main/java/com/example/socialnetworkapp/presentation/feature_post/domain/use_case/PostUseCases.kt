package com.example.socialnetworkapp.presentation.feature_post.domain.use_case

data class PostUseCases(
    val getPostForFollowsUseCase: GetPostForFollowsUseCase,
    val createPostUseCase: CreatePostUseCase,
    val getPostDetails: GetPostDetailsUseCase,
    val getCommentsForPost: GetCommentsForPostUseCase
)