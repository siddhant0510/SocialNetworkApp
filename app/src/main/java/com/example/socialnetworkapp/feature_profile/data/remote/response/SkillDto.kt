package com.example.socialnetworkapp.feature_profile.data.remote.response

import com.example.socialnetworkapp.feature_profile.domain.model.Skill
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