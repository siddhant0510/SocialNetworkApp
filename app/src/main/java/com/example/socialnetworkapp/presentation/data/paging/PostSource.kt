package com.example.socialnetworkapp.presentation.data.paging

import android.provider.SyncStateContract
import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import com.example.socialnetworkapp.domain.models.Post
import com.example.socialnetworkapp.presentation.data.data_source.remote.PostApi
import com.example.socialnetworkapp.utli.Constants
import okio.IOException

class PostSource(
    private val api: PostApi
) : PagingSource<Int, Post>() {

    private var currentPage = 0
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val nextPage = params.key ?: currentPage
            val posts = api.getPostsForFollows(
                page = nextPage,
                pageSize = Constants.PAGE_SIZE_POSTS
            )
            LoadResult.Page(
                data = posts,
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = if(posts.isEmpty()) null else currentPage + 1
            ).also { currentPage++ }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition
    }

}