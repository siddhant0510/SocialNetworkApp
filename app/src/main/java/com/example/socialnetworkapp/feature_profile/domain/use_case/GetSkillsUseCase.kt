package com.example.socialnetworkapp.feature_profile.domain.use_case

import com.example.socialnetworkapp.feature_profile.domain.model.Skill
import com.example.socialnetworkapp.feature_profile.domain.repository.ProfileRepository
import com.example.socialnetworkapp.utli.Resource

class GetSkillsUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Resource<List<Skill>> {
        return repository.getSkills()
    }
}