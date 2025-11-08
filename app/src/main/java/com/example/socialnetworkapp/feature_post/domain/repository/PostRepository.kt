package com.example.socialnetworkapp.feature_post.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.example.socialnetworkapp.domain.models.Comment
import com.example.socialnetworkapp.domain.models.Post
import com.example.socialnetworkapp.utli.Resource
import com.example.socialnetworkapp.utli.SimpleResource
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    val posts: Flow<PagingData<Post>>

    suspend fun createPost(description: String, imageUri: Uri): SimpleResource

    suspend fun getPostDetails(postId: String): Resource<Post>

    suspend fun getCommentsForPost(postId: String): Resource<List<Comment>>

    suspend fun createComment(postId: String, comment: String): SimpleResource

    suspend fun likeParent(parentId: String, parentType: Int): SimpleResource

    suspend fun unlikeParent(parentId: String, parentType: Int): SimpleResource
}