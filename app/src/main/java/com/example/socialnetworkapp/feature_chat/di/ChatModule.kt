package com.example.socialnetworkapp.feature_chat.di

import android.app.Application
import com.example.socialnetworkapp.feature_chat.data.remote.ws.util.CustomGsonMessageAdapter
import com.example.socialnetworkapp.feature_chat.data.remote.ws.util.FlowStreamAdapter
import com.google.gson.Gson
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@Module
@InstallIn(SingletonComponent::class)
object ChatModule {

    @Provides
    @Singleton
    fun provideScarlet(app: Application, gson: Gson, client: OkHttpClient): Scarlet {
        return Scarlet.Builder()
            .addMessageAdapterFactory(CustomGsonMessageAdapter.Factory())
            .addStreamAdapterFactory(FlowStreamAdapter.Factory)
            .webSocketFactory(
                client.newWebSocketFactory("ws://192.168.0.2:8001/api/chats/message")
            )
            .lifecycle(AndroidLifecycle.ofApplicationForeground(app))
            .build()
    }
}