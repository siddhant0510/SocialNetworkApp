package com.example.socialnetworkapp.feature_post.data.repository

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import coil.network.HttpException
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.models.Comment
import com.example.socialnetworkapp.domain.models.Post
import com.example.socialnetworkapp.domain.models.UserItem
import com.example.socialnetworkapp.domain.util.getFileName
import com.example.socialnetworkapp.feature_post.data.remote.PostApi
import com.example.socialnetworkapp.feature_post.data.remote.request.CreateCommentRequest
import com.example.socialnetworkapp.feature_post.data.remote.request.CreatePostRequest
import com.example.socialnetworkapp.feature_post.data.remote.request.LikeUpdateRequest
import com.example.socialnetworkapp.feature_post.domain.repository.PostRepository
import com.example.socialnetworkapp.utli.Resource
import com.example.socialnetworkapp.utli.SimpleResource
import com.example.socialnetworkapp.utli.UiText
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.IOException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class PostRepositoryImpl(
    private val api: PostApi,
    private val gson: Gson,
    private val appContext: Context
) : PostRepository {

    override suspend fun getPostsForFollows(
        page: Int,
        pageSize: Int
    ): Resource<List<Post>> {
        return try {
            val posts = api.getPostsForProfile(
                page = page,
                pageSize = pageSize,
                userId = ""
            )
            Resource.Success(data = posts)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun createPost(
        description: String,
        imageUri: Uri
    ): SimpleResource {
        val request = CreatePostRequest(description)
        val file = withContext(Dispatchers.IO) {
            appContext.contentResolver.openFileDescriptor(imageUri, "r")?.let { fd ->
                val inputStream = FileInputStream(fd.fileDescriptor)
                val file = File(
                    appContext.cacheDir,
                    appContext.contentResolver.getFileName(imageUri)
                )
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)
                file
            }
        } ?: return Resource.Error(
            uiText = UiText.StringResource(R.string.error_file_not_found)
        )

        return try{
            val response = api.createPost(
                postData = MultipartBody.Part
                    .createFormData(
                        "post_data",
                        gson.toJson(request)
                    ),
                postImage = MultipartBody.Part
                    .createFormData(
                        name = "post_image",
                        filename = file.name,
                        body = file.asRequestBody()
                    )
            )
            if(response.successful) {
                Resource.Success(Unit)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknow))
            }
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

    override suspend fun getPostDetails(postId: String): Resource<Post> {
        return try{
            val response = api.getPostDetails(postId = postId)
            if(response.successful) {
                Resource.Success(response.data)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknow))
            }
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

    override suspend fun getCommentsForPost(postId: String): Resource<List<Comment>> {
        return try{
            val comments = api.getCommentsForPost(postId = postId).map {
                it.toComment()
            }
            Resource.Success(comments)
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

    override suspend fun createComment(
        postId: String,
        comment: String
    ): SimpleResource {
        return try{
            val response = api.createComment(
                CreateCommentRequest(
                    comment = comment,
                    postId = postId
                )
            )
            if(response.successful) {
                Resource.Success(response.data)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknow))
            }
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

    override suspend fun likeParent(
        parentId: String,
        parentType: Int
    ): SimpleResource {
        return try{
            val response = api.likeParent(
                LikeUpdateRequest(
                    parentId = parentId,
                    parentType = parentType
                )
            )
            if(response.successful) {
                Resource.Success(response.data)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknow))
            }
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

    override suspend fun unlikeParent(
        parentId: String,
        parentType: Int
    ): SimpleResource {
        return try{
            val response = api.unlikedParent(
                parentId = parentId,
                parentType = parentType
            )
            if(response.successful) {
                Resource.Success(response.data)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknow))
            }
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

    override suspend fun getLikesForPostParent(parentId: String): Resource<List<UserItem>> {
        return try {
            val response = api.getLikesForParent(
                parentId = parentId
            )
            Resource.Success(response.map { it.toUserItem() })
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }
}