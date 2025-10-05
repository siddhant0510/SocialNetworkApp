package com.example.socialnetworkapp.presentation.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
//import com.example.socialnetworkapp.presentation.componenets.StandardScaffold

@Composable
fun ChatScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(text = "Chat Screen")
    }
}