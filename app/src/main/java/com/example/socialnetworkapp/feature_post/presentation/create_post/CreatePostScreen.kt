package com.example.socialnetworkapp.feature_post.presentation.create_post

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.feature_post.presentation.util.PostConstants
import com.example.socialnetworkapp.feature_post.presentation.util.PostDescriptionError
import com.example.socialnetworkapp.presentation.componenets.StandardTextField
import com.example.socialnetworkapp.presentation.componenets.StandardToolbar
import com.example.socialnetworkapp.presentation.theme.SpaceLarge
import com.example.socialnetworkapp.presentation.theme.SpaceMedium
import com.example.socialnetworkapp.presentation.theme.SpaceSmall
import com.example.socialnetworkapp.presentation.util.CropActivityResultContract
import com.example.socialnetworkapp.presentation.util.UiEvent
import com.example.socialnetworkapp.presentation.util.asString
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun CreatePostScreen(
    imageLoader: ImageLoader,
    onNavigateUp: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
    scaffoldState: SnackbarHostState,
    viewModel: CreatePostViewModel = hiltViewModel()
){
    val imageUri = viewModel.chosenImageUri.value

    val cropActivityLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(16f, 9f)
    ) {
        viewModel.onEvent(CreatePostEvent.CropImage(it))
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.onEvent(CreatePostEvent.PickImage(it))
            cropActivityLauncher.launch(it)
        }
    }
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.ShowSnakbar -> {
                    GlobalScope.launch {
                        scaffoldState.showSnackbar(
                            message = event.uiText.asString(context)
                        )
                    }
                }
                is UiEvent.NavigateUp -> {
                    onNavigateUp()
                }

                is UiEvent.Navigate -> Unit
                UiEvent.OnLogin -> Unit
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(top = 48.dp)
    ){
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(id = R.string.create_new_post),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            navActions = {
                IconButton(onClick = {} ){
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.create_post),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    imageUri?.let { uri ->
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = uri,
                                imageLoader = imageLoader
                            ),
                            contentDescription = stringResource(id = R.string.post_image),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        )
        Spacer(modifier = Modifier.Companion.height(SpaceMedium))
        Column (
            modifier = Modifier.fillMaxSize().padding(SpaceLarge)
        ){
            Box(
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = MaterialTheme.shapes.medium
                    )
                    .clickable {
                        galleryLauncher.launch("image/*")
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
            Spacer(modifier = Modifier.Companion.height(SpaceMedium))
            StandardTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                text = viewModel.descriptionState.value.text,
                hint = stringResource(id = R.string.description),
                error = when(viewModel.descriptionState.value.error) {
                    is PostDescriptionError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                    else -> ""
                },
                singleLine = false,
                maxLength = PostConstants.MAZ_POST_DESCRIPTION_LENGTH,
                leadingIcon = Icons.Default.Description,
                onValueChange = {
                    viewModel.onEvent(
                        CreatePostEvent.EnterDescription(it)
                    )
                },
            )
            Spacer(modifier = Modifier.Companion.height(SpaceMedium))
            Button(
                onClick = {
                    viewModel.onEvent(CreatePostEvent.PostImage)
                },
                enabled = !viewModel.isLoading.value,
                modifier = Modifier
                    .align(Alignment.End),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.post),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.Companion.width(SpaceSmall))
                if (viewModel.isLoading.value) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .size(20.dp)
                            .align(CenterVertically)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = stringResource(id = R.string.send)
                    )
                }

            }
        }
    }
