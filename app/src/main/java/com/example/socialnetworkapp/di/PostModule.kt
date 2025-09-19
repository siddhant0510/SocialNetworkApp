package com.example.socialnetworkapp.di

import com.example.socialnetworkapp.data.repository.PostRepositoryImpl
import com.example.socialnetworkapp.domain.repository.PostRepository
import com.example.socialnetworkapp.domain.usecase.GetPostForFollowsUseCase
import com.example.socialnetworkapp.domain.usecase.PostUseCases
import com.example.socialnetworkapp.presentation.data.data_source.remote.PostApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostModule {

    @Provides
    @Singleton
    fun providePostApi(client: OkHttpClient): PostApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostApi.BASE_URL)
            .client(client)
            .build()
            .create(PostApi::class.java)
    }

    @Provides
    @Singleton
    fun providePostRepository(api: PostApi): PostRepository {
        return PostRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providePostUseCases(repository: PostRepository): PostUseCases {
        return PostUseCases(
            getPostForFollowsUseCase = GetPostForFollowsUseCase(repository)
        )
    }
}