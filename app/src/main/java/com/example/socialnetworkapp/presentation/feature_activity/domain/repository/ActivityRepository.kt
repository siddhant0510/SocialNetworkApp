package com.example.socialnetworkapp.presentation.feature_activity.domain.repository

import androidx.paging.PagingData
import com.example.socialnetworkapp.domain.models.Activity
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {

    val activities: Flow<PagingData<Activity>>
}