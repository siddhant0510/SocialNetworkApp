package com.example.socialnetworkapp.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.example.socialnetworkapp.domain.models.Post
import com.example.socialnetworkapp.utli.SimpleResource
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PostRepository {

    val posts: Flow<PagingData<Post>>

    suspend fun createPost(description: String, imageUri: Uri): SimpleResource
}