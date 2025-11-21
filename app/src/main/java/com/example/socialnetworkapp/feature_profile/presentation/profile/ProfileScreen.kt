package com.example.socialnetworkapp.feature_profile.presentation.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.domain.models.User
import com.example.socialnetworkapp.feature_post.presentation.person_list.PostEvent
import com.example.socialnetworkapp.feature_profile.presentation.profile.components.BannerSection
import com.example.socialnetworkapp.feature_profile.presentation.profile.components.ProfileHeaderSection
import com.example.socialnetworkapp.presentation.componenets.Post
import com.example.socialnetworkapp.presentation.theme.ProfilePictureSizeLarge
import com.example.socialnetworkapp.presentation.theme.SpaceMedium
import com.example.socialnetworkapp.presentation.theme.SpaceSmall
import com.example.socialnetworkapp.presentation.util.UiEvent
import com.example.socialnetworkapp.presentation.util.asString
import com.example.socialnetworkapp.utli.Screen
import com.example.socialnetworkapp.utli.sendSharePostIntent
import com.example.socialnetworkapp.utli.toPx
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun ProfileScreen(
    snackbarHostState: SnackbarHostState,
    imageLoader: ImageLoader,
    userId: String? = null,
    onNavigate: (String) -> Unit = {},
    onLogout: () -> Unit = {},
    profilePictureSize: Dp = ProfilePictureSizeLarge,
    viewModel: ProfileViewModel = hiltViewModel()
){
    Column(modifier = Modifier.fillMaxWidth().padding(top = 48.dp)){
        val pagingState = viewModel.pagingState.value
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
                    val shouldNotScroll = delta > 0f &&
                            viewModel.pagingState.value.items.isEmpty() ||
                            lazyListState.firstVisibleItemIndex != 0
                    if(shouldNotScroll){
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
            viewModel.getProfile(userId)
            viewModel.eventFlow.collectLatest { event ->
                when(event) {
                    is UiEvent.ShowSnakbar -> {
                        snackbarHostState.showSnackbar(
                            message = event.uiText.asString(context)
                        )
                    }
                    PostEvent.OnLiked -> {

                    }

                    is UiEvent.Navigate -> Unit
                    UiEvent.NavigateUp -> Unit
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
                            isFollowing = profile.isFollowing,
                            isOwnProfile = profile.isOwnProfile,
                            onEditClick = {
                                onNavigate(Screen.EditProfileScreen.route + "/${profile.userId}")
                            },
                            onLogoutClick = {
                                viewModel.onEvent(ProfileEvent.ShowLogoutDialog)
                            }
                        )
                    }
                }

                items(
                    count = pagingState.items.size
                ) { i ->
                    val post = pagingState.items[i]
                    if(i >= pagingState.items.size - 1 && !pagingState.endReached && !pagingState.isLoading) {
                        viewModel.loadNextPosts()
                    }
                    Post(
                        post = post,
                        imageLoader = imageLoader,
                        showProfileImage = false,
                        onPostClick = {
                            onNavigate(Screen.PostDetailsScreen.route + "/${post.id}")
                        },
                        onCommentClick = {
                            onNavigate(Screen.PostDetailsScreen.route + "/${post.id}?shouldShowKeyboard=true")
                        },
                        onLikeClick = {
                            viewModel.onEvent(ProfileEvent.LikedPost(post.id))
                        },
                        onShareClick = {
                            context.sendSharePostIntent(post.id)
                        },
                        snackbarHostState = snackbarHostState
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(90.dp))
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
                        imageLoader = imageLoader,
                        topSkills = profile.topSkills,
                        shouldShowGitHub = profile.gitHubUrl != null && profile.gitHubUrl.isNotBlank(),
                        shouldShowInstagram = profile.instagramUrl != null && profile.instagramUrl.isNotBlank(),
                        shouldShowLinkedIn = profile.linkedInUrl != null && profile.linkedInUrl.isNotBlank(),
                        bannerUrl = profile.bannerUrl,
                    )
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = profile.profilePictureUrl,
                            imageLoader = imageLoader
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

            if(state.isLogoutDialogVisible) {
                Dialog(onDismissRequest = {
                    viewModel.onEvent(ProfileEvent.DismissLogOutDialog)
                }) {
                    Column(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(SpaceMedium)
                    ) {
                        Text(text = stringResource(R.string.do_you_want_to_logout))
                        Spacer(modifier = Modifier.height(SpaceMedium))
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text(
                                text = stringResource(id = R.string.no).uppercase(),
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable {
                                    viewModel.onEvent(ProfileEvent.DismissLogOutDialog)
                                }
                            )
                            Spacer(modifier = Modifier.width(SpaceMedium))
                            Text(
                                text = stringResource(id = R.string.yes).uppercase(),
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable {
                                    viewModel.onEvent(ProfileEvent.Logout)
                                    viewModel.onEvent(ProfileEvent.DismissLogOutDialog)
                                    onLogout()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}