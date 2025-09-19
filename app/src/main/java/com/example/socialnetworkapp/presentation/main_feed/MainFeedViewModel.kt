package com.example.socialnetworkapp.presentation.main_feed

import androidx.lifecycle.ViewModel
import com.example.socialnetworkapp.domain.usecase.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainFeedViewModel @Inject constructor(
    private val postUseCases: PostUseCases
) : ViewModel() {

}