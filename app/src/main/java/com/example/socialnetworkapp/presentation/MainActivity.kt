package com.example.socialnetworkapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.example.socialnetworkapp.presentation.ui.theme.SocialNetworkAppTheme
import com.example.socialnetworkapp.presentation.util.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SocialNetworkAppTheme {
                Surface (
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .padding(WindowInsets.systemBars.asPaddingValues())
                        .fillMaxSize()
                ){
                    Navigation()
                }
            }
        }
    }
}
