package com.example.socialnetworkapp.feature_profile.presentation.edit_profile

import com.example.socialnetworkapp.feature_profile.domain.model.Skill

data class SkillsState(
    val skills: List<Skill> = emptyList(),
    val selectedSkills: List<Skill> = emptyList()
)
