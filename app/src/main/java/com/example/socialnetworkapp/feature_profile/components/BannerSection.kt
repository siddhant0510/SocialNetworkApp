package com.example.socialnetworkapp.feature_profile.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest.Builder
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.feature_profile.domain.model.Skill
import com.example.socialnetworkapp.theme.SpaceSmall
import com.example.socialnetworkapp.utli.toPx

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun BannerSection(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    iconSize: Dp = 40.dp,
    leftIconModifier: Modifier = Modifier,
    rightIconModifier: Modifier = Modifier,
    bannerUrl: String? = null,
    imageLoader: ImageLoader,
    topSkills: List<Skill> = emptyList(),
    shouldShowGitHub: Boolean,
    shouldShowInstagram: Boolean,
    shouldShowLinkedIn: Boolean,
    onGitHubClick: () -> Unit = {},
    onInstagramClick: () -> Unit = {},
    onLinkedInClick: () -> Unit = {}
){
    val context = LocalContext.current
    BoxWithConstraints(
        modifier = modifier
    ){

        Image(
            painter = rememberAsyncImagePainter(
                model = bannerUrl,
                imageLoader = imageLoader
            ),
            contentDescription = stringResource(id = R.string.banner_image),
            contentScale = ContentScale.Crop,
            modifier = imageModifier
                .fillMaxSize()
        )
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = constraints.maxHeight - iconSize.toPx() * 2f
                    )
                )
        )
//        Row(
//            modifier = leftIconModifier
//                .height(iconSize)
//                .align(Alignment.BottomStart)
//                .padding(SpaceSmall)
//        ){
//            topSkills.forEach { skill ->
//                Spacer(modifier = Modifier.width(SpaceSmall))
//                Image(
//                    painter = rememberAsyncImagePainter(
//                        Builder(LocalContext.current).data(
//                        data = skill.imageUrl,
//                        imageLoader = ImageLoader.Builder(LocalContext.current)
//                            .componentRegistry {
//                                add(SvgDecoder(LocalContext.current))
//                            }
//                            .build()
//                    ).apply(block = { -> crossfade(true) }).build()),
//                    contentDescription = null,
//                    modifier = Modifier.height(iconSize)
//                )
//            }
//        }
        Row(
            modifier = leftIconModifier
                .height(iconSize)
                .align(Alignment.BottomStart)
                .padding(SpaceSmall)
        ) {
            val context = LocalContext.current
            topSkills.forEach { skill ->
                Spacer(modifier = Modifier.Companion.width(SpaceSmall))
                Image(
                    painter = rememberAsyncImagePainter(
                        model = skill.imageUrl,
                        imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    modifier = Modifier.height(iconSize)
                )
            }
        }
        Row(
            modifier = rightIconModifier
                .height(iconSize)
                .align(Alignment.BottomEnd)
                .padding(SpaceSmall)
        ){
            if(shouldShowGitHub) {
                IconButton(
                    onClick = onGitHubClick,
                    modifier = Modifier.size(iconSize)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.github),
                        contentDescription = "GitHub",
                        modifier = Modifier.size(iconSize)
                    )
                }
            }
            if(shouldShowInstagram) {
                IconButton(
                    onClick = onInstagramClick,
                    modifier = Modifier.size(iconSize)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.instagram),
                        contentDescription = "Instagram",
                        modifier = Modifier.size(iconSize)
                    )
                }
            }
            if(shouldShowLinkedIn) {
                IconButton(
                    onClick = onLinkedInClick,
                    modifier = Modifier.size(iconSize)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.linkedin),
                        contentDescription = "Linked In",
                        modifier = Modifier.size(iconSize)
                    )
                }
            }
        }
    }
}