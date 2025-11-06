package com.example.socialnetworkapp.presentation.feature_profile.presentation.edit_profile

import com.example.socialnetworkapp.presentation.feature_profile.domain.model.Skill

data class SkillsState(
    val skills: List<Skill> = emptyList(),
    val selectedSkills: List<Skill> = emptyList()
)
