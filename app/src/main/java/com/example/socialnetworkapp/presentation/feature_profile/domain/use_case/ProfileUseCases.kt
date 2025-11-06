package com.example.socialnetworkapp.presentation.feature_profile.domain.use_case

import com.example.socialnetworkapp.presentation.feature_profile.domain.use_case.ToggleFollowStateForUserUseCase

data class ProfileUseCases(
    val getProfile: GetProfileUseCase,
    val updateProfile: UpdateProfileUseCase,
    val getSkills: GetSkillsUseCase,
    val setSkillSelected: SetSkillSelectedUseCase,
    val getPostsForProfile: GetPostForProfileUseCase,
    val searchUser: SearchUserUseCase,
    val toggleFollowStateForUser: ToggleFollowStateForUserUseCase
)
