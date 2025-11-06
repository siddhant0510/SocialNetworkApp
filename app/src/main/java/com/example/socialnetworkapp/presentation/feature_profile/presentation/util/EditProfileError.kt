package com.example.socialnetworkapp.presentation.feature_profile.presentation.util

sealed class EditProfileError : Error() {
    object FieldEmpty : EditProfileError()
}