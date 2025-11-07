package com.lebaillyapp.bettydices.ui.composable

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lebaillyapp.bettydices.R


@Composable
fun SpriteButton(
    modifier: Modifier = Modifier,
    @DrawableRes resId: Int,
    text: String? = null,
    @FontRes fontRes: Int? = null,
    textColor: Color = Color.White,
    textSize: TextUnit = 14.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    contentDescription: String? = null,
    onClick: () -> Unit,
    enabled: Boolean = true,
    disabledAlpha: Float = 0.5f,
    size: Dp = 108.dp,
    vibronicType: Int = 0 // 0 = classic, 999 = Roll heavy effect
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val haptic = LocalHapticFeedback.current

    // Animation du "press"
    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.90f else 1f,
        label = "SpriteButtonScale"
    )

    // Micro vibrations
    LaunchedEffect(isPressed) {
        if (!enabled) return@LaunchedEffect


        val feedbackType = if (vibronicType == 999) {
            HapticFeedbackType.LongPress
        } else {
            HapticFeedbackType.TextHandleMove
        }

        haptic.performHapticFeedback(feedbackType)


    }

    val fontFamily = fontRes?.let { FontFamily(Font(it)) } ?: FontFamily.Default

    Box(
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
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(resId),
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        if (text != null) {
            Text(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = text,
                color = textColor,
                fontSize = textSize,
                fontWeight = fontWeight,
                fontFamily = fontFamily,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.99f),
                        offset = Offset(0f, 3f),
                        blurRadius = 1f
                    )
                )
            )
        }
    }
}



@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun SpriteButtonPreview(modifier: Modifier = Modifier) {

    val sizerButt = 90.dp

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 36.dp)
                .fillMaxWidth()
                .background(Color.Black),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
        ) {
            SpriteButton(
                resId = R.drawable.cyb_but_scoreur,
                text = "1923",
                textSize = 30.sp,
                fontRes = R.font.micro_regular,
                textColor = Color.White,
                vibronicType = 999,
                contentDescription = "Enabled",
                onClick = { Log.d("SpriteButton", "Clicked!") },
                size = sizerButt,
                enabled = true,
                disabledAlpha = 0.4f
            )
        }
    }
}

