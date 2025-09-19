package com.example.socialnetworkapp.domain.repository

import com.example.socialnetworkapp.domain.models.Post
import com.example.socialnetworkapp.utli.Constants
import com.example.socialnetworkapp.utli.Resource

interface PostRepository {

    suspend fun getPostsForFollows(
        page: Int = 0,
        pageSize: Int = Constants.PAGE_SIZE_POSTS
    ) : Resource<List<Post>>
}