package com.example.socialnetworkapp.presentation.componenets

import android.util.Log
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun AnnotatedClickableText() {
    val annotatedText = buildAnnotatedString {

        pushStringAnnotation(
            tag = "username",
            annotation = "username"
        )

        withStyle(
            style = SpanStyle(
                color = Color.Red
            )
        ) {
            append("Sign up")
        }
        pop()

        withStyle(
            style = SpanStyle(fontWeight = FontWeight.Bold)
        ) {
            append("Sid")
        }
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(
                tag = "SignUp",
                start = offset,
                end = offset
            )[0].let { annotation ->
                Log.d("Clicked", annotation.item)
            }
        }
    )
}