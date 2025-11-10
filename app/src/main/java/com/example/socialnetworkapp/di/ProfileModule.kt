package com.example.socialnetworkapp.di

import com.example.socialnetworkapp.feature_post.data.remote.PostApi
import com.example.socialnetworkapp.feature_profile.data.remote.ProfileApi
import com.example.socialnetworkapp.feature_profile.data.repository.ProfileRepositoryImpl
import com.example.socialnetworkapp.feature_profile.domain.repository.ProfileRepository
import com.example.socialnetworkapp.feature_profile.domain.use_case.GetPostForProfileUseCase
import com.example.socialnetworkapp.feature_profile.domain.use_case.GetProfileUseCase
import com.example.socialnetworkapp.feature_profile.domain.use_case.GetSkillsUseCase
import com.example.socialnetworkapp.feature_profile.domain.use_case.ProfileUseCases
import com.example.socialnetworkapp.feature_profile.domain.use_case.SearchUserUseCase
import com.example.socialnetworkapp.feature_profile.domain.use_case.SetSkillSelectedUseCase
import com.example.socialnetworkapp.feature_profile.domain.use_case.ToggleFollowStateForUserUseCase
import com.example.socialnetworkapp.feature_profile.domain.use_case.UpdateProfileUseCase
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
    fun provideProfileRepository(profileApi: ProfileApi, postApi: PostApi, gson: Gson): ProfileRepository {
        return ProfileRepositoryImpl(profileApi, postApi, gson)
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repository: ProfileRepository): ProfileUseCases {
        return ProfileUseCases(
            getProfile = GetProfileUseCase(repository),
            getSkills = GetSkillsUseCase(repository),
            updateProfile = UpdateProfileUseCase(repository),
            setSkillSelected = SetSkillSelectedUseCase(),
            getPostsForProfile = GetPostForProfileUseCase(repository),
            searchUser = SearchUserUseCase(repository),
            toggleFollowStateForUser = ToggleFollowStateForUserUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideToggleFollowForUserUseCase(repository: ProfileRepository): ToggleFollowStateForUserUseCase {
        return ToggleFollowStateForUserUseCase(repository)
    }
}