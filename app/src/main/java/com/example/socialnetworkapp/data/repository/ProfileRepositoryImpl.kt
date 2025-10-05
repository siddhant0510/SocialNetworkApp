package com.example.socialnetworkapp.data.repository

import coil.network.HttpException
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.models.Profile
import com.example.socialnetworkapp.domain.repository.ProfileRepository
import com.example.socialnetworkapp.presentation.data.ProfileApi
import com.example.socialnetworkapp.utli.Resource
import com.example.socialnetworkapp.utli.UiText
import okio.IOException

class ProfileRepositoryImpl(
    private val api: ProfileApi
) : ProfileRepository {

    override suspend fun getProfile(userId: String): Resource<Profile> {
        return try{
            val response = api.getProfile(userId)
            if(response.successful) {
                Resource.Success(response.data?.toProfile())
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
}