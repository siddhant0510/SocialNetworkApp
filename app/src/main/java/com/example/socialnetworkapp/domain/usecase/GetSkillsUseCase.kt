package com.example.socialnetworkapp.domain.usecase

import com.example.socialnetworkapp.domain.models.Skill
import com.example.socialnetworkapp.domain.repository.ProfileRepository
import com.example.socialnetworkapp.utli.Resource

class GetSkillsUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Resource<List<Skill>> {
        return repository.getSkills()
    }
}