package com.example.socialnetworkapp.presentation.componenets

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.socialnetworkapp.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StandardScaffold(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
){

    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surface,
                cutoutShape = CircleShape,
                elevation = 5.dp
            ) {
                BottomNavigation(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = MaterialTheme.colorScheme.surface,
                ) {
                    StandardBottomNavItem(
                        icon = Icons.Outlined.Home,
                        contentDescription = stringResource(id = R.string.home),
                        selected = true,
                        alertCount = 71,
                    ) {

                    }
                    StandardBottomNavItem(
                        icon = Icons.Outlined.Message,
                        contentDescription = stringResource(id = R.string.chat),
                        selected = false,
                    ) {

                    }
                }
            }
        },
        modifier = modifier
    ){
        content()
    }
}