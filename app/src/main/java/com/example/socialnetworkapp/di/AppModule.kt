package com.example.socialnetworkapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.unit.Constraints
import com.example.socialnetworkapp.utli.Constants
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
    fun provideJwtToken(sharedPreferences: SharedPreferences): String {
        return sharedPreferences.getString(Constants.KEY_JWT_TOKEN, "") ?: ""
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(prefs: SharedPreferences): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(prefs))
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }
}