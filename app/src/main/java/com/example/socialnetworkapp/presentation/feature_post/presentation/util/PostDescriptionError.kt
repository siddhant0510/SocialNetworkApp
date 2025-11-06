package com.example.socialnetworkapp.presentation.feature_post.presentation.util

sealed class PostDescriptionError : Error() {
    object FieldEmpty: PostDescriptionError()
}