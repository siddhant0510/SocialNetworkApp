package com.example.socialnetworkapp.feature_post.presentation.person_list

import com.example.socialnetworkapp.utli.Event

sealed class PostEvent : Event() {
    object OnLiked: PostEvent()
}