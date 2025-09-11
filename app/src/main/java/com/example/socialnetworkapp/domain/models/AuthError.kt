package com.example.socialnetworkapp.domain.models

sealed class AuthError : Error() {
    object FieldEmpty : AuthError()
    object InputTooShort : AuthError()
    object InvalidEmail: AuthError()
    object InvalidPassword: AuthError()
}