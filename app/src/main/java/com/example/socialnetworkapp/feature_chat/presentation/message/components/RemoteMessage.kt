package com.example.socialnetworkapp.feature_chat.presentation.message.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.socialnetworkapp.presentation.theme.SpaceLarge
import com.example.socialnetworkapp.presentation.theme.SpaceMedium

@Composable
fun RemoteMessage(
    message: String,
    formatedTime: String,
    modifier: Modifier = Modifier,
    color: Color = Color.DarkGray,
    textColor: Color = MaterialTheme.colorScheme.surface,
    tringleWidth: Dp = 30.dp,
    tringleHeight: Dp = 30.dp
) {

    val cornerRadius = MaterialTheme.shapes.medium.bottomStart
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(SpaceMedium)
                .drawBehind {
                    val cornerRadiusPx = cornerRadius.toPx(
                        shapeSize = size,
                        density = Density(density)
                    )
                    val path = Path().apply {
                        moveTo(
                            0f,
                            size.height - cornerRadiusPx
                        )
                        lineTo(0f, size.height + tringleHeight.toPx())
                        lineTo(
                            tringleWidth.toPx(),
                            size.height - cornerRadiusPx
                        )
                        close()
                    }
                    drawPath(
                        path = path,
                        color = color
                    )
                }
        ) {
            Text(text = message, color = textColor)
        }
        Spacer(modifier = Modifier.width(SpaceLarge))
        Text(
            text = formatedTime,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.align(Alignment.Bottom)
        )
    }
}
