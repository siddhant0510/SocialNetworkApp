package com.example.socialnetworkapp.data.repository

import android.content.SharedPreferences
import coil.network.HttpException
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.repository.AuthRepository
import com.example.socialnetworkapp.presentation.data.dto.request.CreateAccountRequest
import com.example.socialnetworkapp.presentation.data.dto.request.LoginRequest
import com.example.socialnetworkapp.presentation.data.AuthApi
import com.example.socialnetworkapp.utli.Constants
import com.example.socialnetworkapp.utli.Resource
import com.example.socialnetworkapp.utli.SimpleResource
import com.example.socialnetworkapp.utli.UiText
import okio.IOException

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences
): AuthRepository {

    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): SimpleResource {
        val request = CreateAccountRequest(email, username, password)
        return try{
            val response = api.register(request)
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

    override suspend fun login(
        email: String,
        password: String
    ): SimpleResource {
        val request = LoginRequest(email, password)
        return try{
            val response = api.login(request)
            if(response.successful) {
                response.data?.token?.let { token ->
                    sharedPreferences.edit()
                        .putString(Constants.KEY_JWT_TOKEN, token)
                        .apply()
                }
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

    override suspend fun authenticate(): SimpleResource {
        return try {
            api.authenticate()
            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.some_thing_went_wrong)
            )
        }
    }
}