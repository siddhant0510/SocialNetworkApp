package com.example.socialnetworkapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Constraints
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.example.socialnetworkapp.domain.repository.ProfileRepository
import com.example.socialnetworkapp.domain.usecase.GetOwnUserIdUseCase
import com.example.socialnetworkapp.utli.Constants
import com.example.socialnetworkapp.utli.DefaultPostLiker
import com.example.socialnetworkapp.utli.PostLiker
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(
            Constants.SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(prefs: SharedPreferences): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {
                val token = prefs.getString(Constants.KEY_JWT_TOKEN, "")
                val modifiedRequest = it.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                it.proceed(modifiedRequest)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideImageLoader(app: Application): ImageLoader {
        return ImageLoader.Builder(app)
            .components { add(SvgDecoder.Factory()) }
            .build()
    }

    @Provides
    @Singleton
    fun providePostLiker(): PostLiker {
        return DefaultPostLiker()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideGetOwnUserIdUseCase(sharedPreferences: SharedPreferences): GetOwnUserIdUseCase {
        return GetOwnUserIdUseCase(sharedPreferences)
    }
}