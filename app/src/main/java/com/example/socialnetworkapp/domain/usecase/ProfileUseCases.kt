package com.example.socialnetworkapp.domain.usecase

data class ProfileUseCases(
    val getProfile: GetProfileUseCase,
    val updateProfile: UpdateProfileUseCase,
    val getSkills: GetSkillsUseCase
)
