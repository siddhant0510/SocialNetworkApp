package com.example.socialnetworkapp.presentation.edit_profile

import com.example.socialnetworkapp.domain.models.Skill

data class SkillsState(
    val skills: List<Skill> = emptyList(),
    val selectedSkills: List<Skill> = emptyList()
)
