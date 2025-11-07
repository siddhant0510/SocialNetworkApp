package com.example.socialnetworkapp.feature_post.presentation.util

import com.example.socialnetworkapp.utli.Error

sealed class CommentError : Error() {
    object FieldEmpty: CommentError()
}