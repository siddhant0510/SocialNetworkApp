package com.example.socialnetworkapp.domain.usecase

import android.content.SharedPreferences
import com.example.socialnetworkapp.utli.Constants

class GetOwnUserIdUseCase(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(): String {
        return sharedPreferences.getString(Constants.KEY_USER_ID, "") ?: ""
    }
}