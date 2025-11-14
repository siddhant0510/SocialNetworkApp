package com.example.socialnetworkapp.feature_profile.presentation.profile

sealed class ProfileEvent {
    data class LikedPost(val postId: String): ProfileEvent()
    object DismissLogOutDialog: ProfileEvent()
    object ShowLogoutDialog: ProfileEvent()
    object Logout: ProfileEvent()
}