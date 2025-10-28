package com.example.socialnetworkapp.data.repository

import android.net.Uri
import androidx.core.net.toFile
import coil.network.HttpException
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.models.Profile
import com.example.socialnetworkapp.domain.models.Skill
import com.example.socialnetworkapp.domain.repository.ProfileRepository
import com.example.socialnetworkapp.presentation.data.ProfileApi
import com.example.socialnetworkapp.presentation.edit_profile.request.UpdateProfileData
import com.example.socialnetworkapp.utli.Resource
import com.example.socialnetworkapp.utli.SimpleResource
import com.example.socialnetworkapp.utli.UiText
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.IOException

class ProfileRepositoryImpl(
    private val api: ProfileApi,
    private val gson: Gson
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

    override suspend fun getSkills(): Resource<List<Skill>> {
        return try {
            val response = api.getSkills()
            Resource.Success(
                data = response.map { it.toSkill() }
            )
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

    override suspend fun updateProfile(
        updateProfileData: UpdateProfileData,
        bannerImageUri: Uri?,
        profilePictureUri: Uri?
    ): SimpleResource {
        val bannerFile = bannerImageUri?.toFile()
        val profilePictureFile = profilePictureUri?.toFile()

        return try{
            val response = api.updateProfile(
                bannerImage = bannerFile?.let {
                    MultipartBody.Part
                        .createFormData(
                            "banner_image",
                            bannerFile.name,
                            bannerFile.asRequestBody()
                        )
                },
                profilePicture = profilePictureFile?.let {
                    MultipartBody.Part
                        .createFormData(
                            "profile_picture",
                            profilePictureFile.name,
                            profilePictureFile.asRequestBody()
                        )
                },
                updateProfileData = MultipartBody.Part
                    .createFormData(
                        "update_profile_data",
                        gson.toJson(updateProfileData)
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
}