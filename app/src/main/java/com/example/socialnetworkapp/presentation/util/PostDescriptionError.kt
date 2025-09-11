package com.example.socialnetworkapp.presentation.util

sealed class PostDescriptionError : Error() {
    object FieldEmpty: PostDescriptionError()
}