package com.example.socialnetworkapp.feature_profile.presentation.util

sealed class EditProfileError : Error() {
    object FieldEmpty : EditProfileError()
}