package com.example.socialnetworkapp.data.repository

import coil.network.HttpException
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.models.Post
import com.example.socialnetworkapp.domain.repository.PostRepository
import com.example.socialnetworkapp.presentation.data.data_source.remote.PostApi
import com.example.socialnetworkapp.presentation.data.dto.request.LoginRequest
import com.example.socialnetworkapp.utli.Constants
import com.example.socialnetworkapp.utli.Resource
import com.example.socialnetworkapp.utli.UiText
import okio.IOException

class PostRepositoryImpl(
    private val api: PostApi
) : PostRepository {

    override suspend fun getPostsForFollows(
        page: Int,
        pageSize: Int
    ): Resource<List<Post>> {
        return try{
            val posts = api.getPostsForFollows(page, pageSize)
            Resource.Success(posts)
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.some_thing_went_wrong)
            )
        }
    }
}