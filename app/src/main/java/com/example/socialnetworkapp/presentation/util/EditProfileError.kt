package com.example.socialnetworkapp.presentation.util

sealed class EditProfileError : Error() {
    object FieldEmpty : EditProfileError()
}
