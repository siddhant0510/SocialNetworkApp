package com.example.socialnetworkapp.feature_profile.presentation.search

import com.example.socialnetworkapp.utli.Error
import com.example.socialnetworkapp.utli.UiText

data class SearchError(
    val message: UiText
): Error()
