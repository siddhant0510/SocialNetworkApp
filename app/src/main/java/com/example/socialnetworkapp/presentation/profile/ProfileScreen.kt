package com.example.socialnetworkapp.presentation.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.models.User
import com.example.socialnetworkapp.presentation.componenets.Post
import com.example.socialnetworkapp.presentation.profile.components.BannerSection
import com.example.socialnetworkapp.presentation.profile.components.ProfileHeaderSection
import com.example.socialnetworkapp.presentation.ui.theme.ProfilePictureSizeLarge
import com.example.socialnetworkapp.presentation.ui.theme.SpaceMedium
import com.example.socialnetworkapp.presentation.ui.theme.SpaceSmall
import com.example.socialnetworkapp.presentation.util.Screen
import com.example.socialnetworkapp.presentation.util.UiEvent
import com.example.socialnetworkapp.presentation.util.asString
import com.example.socialnetworkapp.presentation.util.toPx
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun ProfileScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    snackbarHostState: SnackbarHostState,
    profilePictureSize: Dp = ProfilePictureSizeLarge,
    viewModel: ProfileViewModel = hiltViewModel()
){
    Column(modifier = Modifier.fillMaxWidth().padding(top = 48.dp)){
        val lazyListState = rememberLazyListState()
        val toolbarState = viewModel.toolbarState.value

        val iconHorizontalCenterLength =
            (LocalConfiguration.current.screenWidthDp.dp.toPx() / 4f -
                    (profilePictureSize / 4f).toPx() -
                    SpaceSmall.toPx()) / 2f

        val iconSizeExpanded = 40.dp
        val toolbarHeightCollapsed = 75.dp
        val imageCollapsedOffsetY = remember {
            (toolbarHeightCollapsed - profilePictureSize / 2f) / 2f
        }
        val iconCollapsedOffsetY = remember {
            (toolbarHeightCollapsed - iconSizeExpanded) / 2f
        }
        val bannerHeight = (LocalConfiguration.current.screenWidthDp / 2.5f).dp
        val toolbarHeightExpanded = remember {
            bannerHeight + profilePictureSize
        }

        val maxOffset = remember {
            toolbarHeightExpanded - toolbarHeightCollapsed
        }
        val state = viewModel.state.value

        val nestedScrollConnection = remember {
            object : NestedScrollConnection{
                override fun onPreScroll(available: Offset, source: NestedScrollSource) : Offset{
                    val delta = available.y
                    if(delta > 0f && lazyListState.firstVisibleItemIndex != 0){
                        return Offset.Zero
                    }
                    val newOffSet = viewModel.toolbarState.value.toolbarOffsetY + delta
                    viewModel.setToolbarOffsetY(newOffSet.coerceIn(
                        minimumValue = -maxOffset.toPx(),
                        maximumValue = 0f
                    ))
                    viewModel.setExpandedRatio((viewModel.toolbarState.value.toolbarOffsetY + maxOffset.toPx()) / maxOffset.toPx())
                    return Offset.Zero
                }
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

                    is UiEvent.Navigate -> TODO()
                    UiEvent.NavigateUp -> TODO()
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
        ){
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize(),
            ){
                item {
                    Spacer(modifier = Modifier.height(toolbarHeightExpanded - profilePictureSize/2f))
                }
                item {
                    state.profile?.let { profile ->
                        ProfileHeaderSection(
                            user = User(
                                userId = profile.userId,
                                profilePictureUrl = profile.profilePictureUrl,
                                username = profile.username,
                                description = profile.bio,
                                followerCount = profile.followerCount,
                                followingCount = profile.followingCount,
                                postCount = profile.postCount
                            ),
                            isOwnProfile = profile.isOwnProfile,
                            onEditClick = {
                                onNavigate(Screen.EditProfileScreen.route)
                            }
                        )
                    }
                }
                items(20){
                    Spacer(
                        modifier = Modifier
                            .height(SpaceMedium),
                    )
                    Post(
                        post = com.example.socialnetworkapp.domain.models.Post(
                            username = "Siddhant Kudale",
                            imageUrl = "",
                            profilePictureUrl = "",
                            description = "This is a description This is a description " +
                                    "This is a description This is a description This is " +
                                    "a description This is a description This is a description...",
                            likeCount = 17,
                            commentCount = 7
                        ),
                        showProfileImage = false,
                        onPostClick = {
                            onNavigate(Screen.PostDetailsScreen.route)
                        },
                    )
                }
            }
            Column (
                modifier = Modifier
                    .align(Alignment.TopCenter)
            ){
                state.profile?.let { profile ->
                    BannerSection(
                        modifier = Modifier
                            .height(
                                (bannerHeight * toolbarState.expandedRatio).coerceIn(
                                    minimumValue = toolbarHeightCollapsed,
                                    maximumValue = bannerHeight
                                )
                            ),
                        leftIconModifier = Modifier
                            .graphicsLayer {
                                translationY = (1f - toolbarState.expandedRatio) *
                                        -iconCollapsedOffsetY.toPx()
                                translationX = (1f - toolbarState.expandedRatio) *
                                        iconHorizontalCenterLength

                            },
                        rightIconModifier = Modifier
                            .graphicsLayer {
                                translationY = (1f - toolbarState.expandedRatio) *
                                        -iconCollapsedOffsetY.toPx()
                                translationX = (1f - toolbarState.expandedRatio) *
                                        -iconHorizontalCenterLength
                            },
                        topSkillsUrls = profile.topSkills,
                        shouldShowGitHub = profile.gitHubUrl != null,
                        shouldShowInstagram = profile.instagramUrl != null,
                        shouldShowLinkedIn = profile.linkedInUrl != null,
                        bannerUrl = profile.bannerUrl,
                    )
                    Image(
                        painter = rememberImagePainter(
                            data = profile.profilePictureUrl,
                        ),
                        contentDescription = stringResource(id = R.string.profile_image),
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .graphicsLayer {
                                translationY = -profilePictureSize.toPx() / 2f -
                                        (1f - toolbarState.expandedRatio) * imageCollapsedOffsetY.toPx()
                                transformOrigin = TransformOrigin(
                                    pivotFractionX = 0.5f,
                                    pivotFractionY = 0f
                                )
                                val scale = 0.5f + toolbarState.expandedRatio * 0.5f
                                scaleX = scale
                                scaleY = scale
                            }
                            .size(profilePictureSize)
                            .clip(CircleShape)
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.onSurface,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}