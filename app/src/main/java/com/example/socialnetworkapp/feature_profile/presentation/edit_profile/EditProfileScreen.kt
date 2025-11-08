package com.example.socialnetworkapp.feature_profile.presentation.edit_profile

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest.Builder
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.feature_profile.presentation.edit_profile.components.Chip
import com.example.socialnetworkapp.feature_profile.presentation.util.EditProfileError
import com.example.socialnetworkapp.presentation.componenets.StandardTextField
import com.example.socialnetworkapp.presentation.componenets.StandardToolbar
import com.example.socialnetworkapp.theme.ProfilePictureSizeLarge
import com.example.socialnetworkapp.theme.SpaceLarge
import com.example.socialnetworkapp.theme.SpaceMedium
import com.example.socialnetworkapp.theme.SpaceSmall
import com.example.socialnetworkapp.utilNew.CropActivityResultContract
import com.example.socialnetworkapp.utilNew.UiEvent
import com.example.socialnetworkapp.utilNew.asString
import kotlinx.coroutines.flow.collectLatest
import kotlin.collections.forEach

@Composable
fun EditProfileScreen(
    snackbarHostState: SnackbarHostState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: EditProfileViewModel = hiltViewModel(),
    profilePictureSize: Dp = ProfilePictureSizeLarge
){
    val profileState = viewModel.profileState.value

    val cropProfilePictureLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(1f, 1f)
    ) {
        viewModel.onEvent(EditProfileEvent.CropProfilePicture(it))
    }
    val cropBannerImageLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(5f, 2f)
    ) {
        viewModel.onEvent(EditProfileEvent.CropBannerImage  (it))
    }
    val profilePictureGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            cropProfilePictureLauncher.launch(it)
        }
    }
    val bannerImageGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri ->
        uri?.let {
            cropBannerImageLauncher.launch(it)
        }
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.ShowSnakbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }

                is UiEvent.Navigate -> Unit
                UiEvent.NavigateUp -> {
                    onNavigateUp()
                }
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(top = 48.dp)
    ){
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            navActions = {
                IconButton(onClick = {
                    viewModel.onEvent(EditProfileEvent.UpdateProfile)
                }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.check_icon)
                    )
                }
            },
            title = {
                Text(
                    text = stringResource(id = R.string.edit_your_profile),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ){
            BannerEditSection(
                bannerImage = rememberAsyncImagePainter(
                    model = Builder(LocalContext.current).data(
                    data = viewModel.bannerUri.value ?: profileState.profile?.bannerUrl
                ).apply(block = { -> crossfade(true) }).build()),

                profileImage = rememberAsyncImagePainter(
                    model = Builder(LocalContext.current).data(
                    data = viewModel.profilePictureUri.value ?: profileState.profile?.profilePictureUrl
                ).apply(block = { -> crossfade(true) }).build()),
                profilePictureSize = profilePictureSize,
                onBannerClick = {
                    bannerImageGalleryLauncher.launch("image/*")
                },
                onProfilePictureClick = {
                    profilePictureGalleryLauncher.launch("image/*")
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceLarge)
            ){
                Spacer(modifier = Modifier.Companion.height(SpaceMedium))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.usernameState.value.text,
                    hint = stringResource(id = R.string.username),
                    error = when(viewModel.usernameState.value.error) {
                        is EditProfileError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                        else -> ""
                    },
                    leadingIcon = Icons.Default.Person,
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.EnteredUsername(it)
                        )
                    },
                )
                Spacer(modifier = Modifier.Companion.height(SpaceMedium))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.githubTextFieldState.value.text,
                    hint = stringResource(id = R.string.git_hub_profile_url),
                    error = when(viewModel.githubTextFieldState.value.error) {
                        is EditProfileError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                        else -> ""
                    },
                    leadingIcon = ImageVector.vectorResource(id = R.drawable.github),
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.EnteredGitHubUrl(it)
                        )
                    },
                )
                Spacer(modifier = Modifier.Companion.height(SpaceMedium))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.instagramTextFieldState.value.text,
                    hint = stringResource(id = R.string.instagram_profile_url),
                    error = when(viewModel.instagramTextFieldState.value.error) {
                        is EditProfileError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                        else -> ""
                    },
                    leadingIcon = ImageVector.vectorResource(id = R.drawable.instagram),
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.EnteredInstagramUrl(it)
                        )
                    },
                )
                Spacer(modifier = Modifier.Companion.height(SpaceMedium))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.linkedInTextFiledState.value.text,
                    hint = stringResource(id = R.string.linked_in_profile_url),
                    error = when(viewModel.linkedInTextFiledState.value.error) {
                        is EditProfileError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                        else -> ""
                    },
                    leadingIcon = ImageVector.vectorResource(id = R.drawable.linkedin),
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.EnteredLinkedInUrl(it)
                        )
                    },
                )
                Spacer(modifier = Modifier.Companion.height(SpaceMedium))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.bioState.value.text,
                    hint = stringResource(id = R.string.bio),
                    error = when(viewModel.bioState.value.error) {
                        is EditProfileError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                        else -> ""
                    },
                    singleLine = false,
                    leadingIcon = Icons.Default.Description,
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.EnteredBio(it)
                        )
                    },
                )
                Spacer(modifier = Modifier.Companion.height(SpaceMedium))
                Text(
                    text = stringResource(id = R.string.select_top_3_skills),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(CenterHorizontally)
                )
                Spacer(modifier = Modifier.Companion.height(SpaceMedium))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(SpaceSmall),
                    verticalArrangement = Arrangement.spacedBy(SpaceSmall),
                ) {
                    viewModel.skills.value.skills.forEach { skill ->
                        Chip(
                            text = skill.name,
                            selected = viewModel.skills.value.selectedSkills.any { it.name == skill.name },
                            onChipClick = {
                                viewModel.onEvent(EditProfileEvent.SetSkillsSelected(skill))
                            }
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.HONEYCOMB_MR2)
@Composable
fun BannerEditSection(
    bannerImage: Painter,
    profileImage: Painter,
    profilePictureSize: Dp = ProfilePictureSizeLarge,
    onBannerClick: () -> Unit = {},
    onProfilePictureClick: () -> Unit = {}
    ){
    val bannerHeight = (LocalConfiguration.current.screenWidthDp / 2.5f).dp
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(bannerHeight + profilePictureSize / 2f)
    ){
        Image(
            painter = bannerImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(bannerHeight)
                .clickable { onBannerClick() }
        )
        Image(
            painter = profileImage,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(profilePictureSize)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = CircleShape
                )
                .clickable { onProfilePictureClick() }
        )
    }
}