package com.example.socialnetworkapp.feature_profile.domain.use_case

import com.example.socialnetworkapp.domain.usecase.ToggleFollowStateForUserUseCase

data class ProfileUseCases(
    val getProfile: GetProfileUseCase,
    val updateProfile: UpdateProfileUseCase,
    val getSkills: GetSkillsUseCase,
    val setSkillSelected: SetSkillSelectedUseCase,
    val getPostsForProfile: GetPostForProfileUseCase,
    val searchUser: SearchUserUseCase,
    val toggleFollowStateForUser: ToggleFollowStateForUserUseCase,
    val logout: LogoutUseCase
)
