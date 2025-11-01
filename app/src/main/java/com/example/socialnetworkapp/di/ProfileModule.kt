package com.example.socialnetworkapp.di

import com.example.socialnetworkapp.data.repository.ProfileRepositoryImpl
import com.example.socialnetworkapp.domain.repository.ProfileRepository
import com.example.socialnetworkapp.domain.usecase.GetProfileUseCase
import com.example.socialnetworkapp.domain.usecase.GetSkillsUseCase
import com.example.socialnetworkapp.domain.usecase.ProfileUseCases
import com.example.socialnetworkapp.domain.usecase.SetSkillSelectedUseCase
import com.example.socialnetworkapp.domain.usecase.UpdateProfileUseCase
import com.example.socialnetworkapp.presentation.data.ProfileApi
import com.google.gson.Gson
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
object ProfileModule {

    @Provides
    @Singleton
    fun provideProfileApi(client: OkHttpClient): ProfileApi {
        return Retrofit.Builder()
            .baseUrl(ProfileApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(api: ProfileApi, gson: Gson): ProfileRepository {
        return ProfileRepositoryImpl(api, gson)
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repository: ProfileRepository): ProfileUseCases {
        return ProfileUseCases(
            getProfile = GetProfileUseCase(repository),
            getSkills = GetSkillsUseCase(repository),
            updateProfile = UpdateProfileUseCase(repository),
            setSkillSelected = SetSkillSelectedUseCase()
        )
    }
}