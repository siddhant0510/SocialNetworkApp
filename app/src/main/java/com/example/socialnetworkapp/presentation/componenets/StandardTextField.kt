package com.example.socialnetworkapp.presentation.componenets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.socialnetworkapp.R

@Composable
fun StandardTextField(
    text: String = "",
    hint: String = "",
    maxLength: Int = 40,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
){
    val isPasswordToggleDisplayed by remember {
        mutableStateOf(keyboardType == KeyboardType.Password)
    }
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    TextField(
        value = text,
        onValueChange = {
            if(it.length <= maxLength){
                onValueChange(it)
            }
        },
        placeholder = {
            Text(
                text = hint,
                style = MaterialTheme.typography.body1
            )
        },
        isError = isError,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
        ),
        visualTransformation = if (!isPasswordVisible && isPasswordToggleDisplayed){
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        singleLine = true,
        trailingIcon = {
            if(isPasswordToggleDisplayed){
                IconButton(
                    onClick = {
                        isPasswordVisible = !isPasswordVisible
                    }
                ) {
                    Icon(
                        imageVector = if(isPasswordVisible){
                            Icons.Filled.VisibilityOff
                        } else {
                            Icons.Filled.Visibility
                        },
                        contentDescription = if(isPasswordVisible){
                            stringResource(id = R.string.password_visible_content_description)
                        } else {
                            stringResource(id = R.string.password_hidden_content_description)
                        }
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
    )
}