package com.example.socialnetworkapp.presentation.edit_profile

import com.example.socialnetworkapp.domain.models.Skill
import com.google.gson.annotations.SerializedName

data class SkillDto(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val imageUrl: String
) {
    fun toSkill(): Skill {
        return Skill(
            name = name,
            imageUrl = imageUrl
        )
    }
}
