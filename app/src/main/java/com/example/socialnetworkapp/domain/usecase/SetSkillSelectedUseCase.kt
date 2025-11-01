package com.example.socialnetworkapp.domain.usecase

import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.models.Skill
import com.example.socialnetworkapp.domain.util.ProfileConstants
import com.example.socialnetworkapp.utli.Resource
import com.example.socialnetworkapp.utli.UiText

class SetSkillSelectedUseCase {

    operator fun invoke(
        selectedSkills: List<Skill>,
        skillToToggle: Skill
    ): Resource<List<Skill>> {
        val skillInList = selectedSkills.find { it.name == skillToToggle.name }
        if(skillInList != null) {
            return Resource.Success(selectedSkills - skillInList)
        }
        return if(selectedSkills.size >= ProfileConstants.MAX_SELECTED_SKILL_COUNT) {
            Resource.Error(uiText = UiText.StringResource(R.string.error_max_skills_selected))
        } else {
            Resource.Success(selectedSkills + skillToToggle)
        }
    }
}