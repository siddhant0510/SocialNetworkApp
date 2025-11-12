package com.example.socialnetworkapp.presentation.componenets

//import androidx.compose.material.MaterialTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.theme.iconSizeMedium

@Composable
fun StandardTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    hint: String = "",
    maxLength: Int = 400,
    error: String = "",
    singleLine: Boolean = true,
    style: TextStyle = TextStyle(
        color = MaterialTheme.colorScheme.onBackground
    ),
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    leadingIcon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPasswordToggleDisplayed: Boolean = keyboardType == KeyboardType.Password,
    isPasswordVisible: Boolean = false,
    onPasswordToggleClick: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester = FocusRequester()
){
    Column (
        modifier = Modifier
            .fillMaxWidth()
    ){
        TextField(
            value = text,
            onValueChange = {
                if(it.length <= maxLength){
                    onValueChange(it)
                }
            },
            textStyle = style,
            placeholder = {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
//            colors = TextFieldDefaults.colors(
//                focusedContainerColor = backgroundColor,
//                unfocusedContainerColor = backgroundColor,
//                disabledContainerColor = backgroundColor
//            ),
            isError = error != "",
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
            ),
            visualTransformation = if (!isPasswordVisible && isPasswordToggleDisplayed){
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            singleLine = singleLine,
            leadingIcon = if(leadingIcon != null){
                val icon: @Composable () -> Unit = {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(iconSizeMedium)
                    )
                }
                icon
            } else null,
            trailingIcon = if (isPasswordToggleDisplayed){
                val icon: @Composable () -> Unit = {
                    IconButton(
                        onClick = {
                            onPasswordToggleClick(!isPasswordVisible)
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
                icon
            } else null,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                errorIndicatorColor = Color.Red,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .focusRestorer(focusRequester = focusRequester),
        )
        if(error.isNotEmpty()){
            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}