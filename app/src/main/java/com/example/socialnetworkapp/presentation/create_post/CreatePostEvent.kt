package com.example.socialnetworkapp.presentation.create_post

import android.net.Uri

sealed class CreatePostEvent {
    data class EnterDescription(val value: String): CreatePostEvent()
    data class PickImage(val uri: Uri?): CreatePostEvent()
    object PostImage: CreatePostEvent()
}