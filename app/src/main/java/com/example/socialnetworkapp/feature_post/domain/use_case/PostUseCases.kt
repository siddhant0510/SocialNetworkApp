package com.example.socialnetworkapp.feature_post.domain.use_case

import com.example.socialnetworkapp.domain.usecase.ToggleLikeForParentUseCase

data class PostUseCases(
    val getPostForFollows: GetPostForFollowsUseCase,
    val createPostUseCase: CreatePostUseCase,
    val getPostDetails: GetPostDetailsUseCase,
    val getCommentsForPost: GetCommentsForPostUseCase,
    val createComment: CreateCommentUseCase,
    val toggleLikeForParent: ToggleLikeForParentUseCase,
    val getLikesForParent: GetLikesForParentUseCase
)