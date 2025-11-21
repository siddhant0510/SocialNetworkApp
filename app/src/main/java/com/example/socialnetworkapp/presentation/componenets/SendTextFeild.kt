package com.example.socialnetworkapp.presentation.componenets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.presentation.theme.SpaceLarge
import com.example.socialnetworkapp.domain.state.StandardTextFieldState

@Composable
fun SendTextField(
    state: StandardTextFieldState,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    hint: String = "",
    isLoading: Boolean = false,
    focusedRequester: FocusRequester = FocusRequester()
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .padding(SpaceLarge),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StandardTextField(
            text = state.text,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            hint = hint,
            focusRequester = focusedRequester
        )
        if(isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .size(24.dp),
                strokeWidth = 2.dp
            )
        } else {
            IconButton(
                onClick = onSend,
                enabled = state.error == null
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    tint = if(state.error == null) {
                        MaterialTheme.colorScheme.primary
                    } else MaterialTheme.colorScheme.onSurface,
                    contentDescription = stringResource(id = R.string.send_comment)
                )
            }
        }
    }
}