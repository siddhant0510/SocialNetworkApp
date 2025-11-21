package com.example.socialnetworkapp.feature_profile.domain.use_case

import com.example.socialnetworkapp.domain.models.UserItem
import com.example.socialnetworkapp.domain.repository.ProfileRepository
import com.example.socialnetworkapp.utli.Resource

class SearchUserUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(query: String): Resource<List<UserItem>> {
        if(query.isBlank()) {
            return Resource.Success(data = emptyList())
        }
        return repository.searchUser(query)
    }
}