package com.lebaillyapp.bettydices.ui.composable

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lebaillyapp.bettydices.R

@Composable
fun SpriteButton(
    modifier: Modifier = Modifier,
    @DrawableRes resId: Int,
    contentDescription: String? = null,
    onClick: () -> Unit,
    enabled: Boolean = true,
    disabledAlpha: Float = 0.5f,
    size: Dp = 108.dp
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val haptic = LocalHapticFeedback.current

    // Animation du "press"
    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.90f else 1f,
        label = "SpriteButtonScale"
    )

    // Détection des transitions de pression pour vibrer
    LaunchedEffect(isPressed) {
        if (!enabled) return@LaunchedEffect
        if (isPressed) {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) // micro pression
        } else {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress) // relâchement doux
        }
    }

    Image(
        painter = painterResource(resId),
        contentDescription = contentDescription,
        modifier = modifier
            .size(size)
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                alpha = if (enabled) 1f else disabledAlpha
            )
            .then(
                if (enabled)
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onClick
                    )
                else Modifier
            ),
        contentScale = ContentScale.Fit
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun SpriteButtonPreview(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically)
    ) {
        SpriteButton(
            resId = R.drawable.cyb_but_dices,
            contentDescription = "Enabled",
            onClick = { Log.d("SpriteButton", "Clicked!") },
            size = 90.dp
        )
        SpriteButton(
            resId = R.drawable.cyb_but_dices,
            contentDescription = "Disabled",
            onClick = {},
            size = 90.dp,
            enabled = false,
            disabledAlpha = 0.4f
        )
    }
}
