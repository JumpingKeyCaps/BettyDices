package com.lebaillyapp.bettydices.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lebaillyapp.bettydices.model.dice.config.CubeConfig
import com.lebaillyapp.bettydices.model.dice.config.DiceAnimationConfig
import com.lebaillyapp.bettydices.model.dice.config.DiceLayerConfig
import com.lebaillyapp.bettydices.model.dice.config.createUniformDice
import com.lebaillyapp.bettydices.model.dice.config.createUniformGhost
import com.lebaillyapp.bettydices.model.dice.state.LayerLockState


@Composable
fun DiceItem(
    modifier: Modifier = Modifier,
    value: Int,
    animationConfig: DiceAnimationConfig = DiceAnimationConfig.idle(0),
    diceSize: Float = 150f
) {
    var currentAnimConfig by remember { mutableStateOf(animationConfig) }
    var currentValue by remember { mutableStateOf(value) }

    LaunchedEffect(animationConfig) {
        currentAnimConfig = animationConfig
    }

    val layers = remember(currentValue) {
        listOf(
            DiceLayerConfig.createGhostParent(),

            DiceLayerConfig(
                cubeConfig = if (currentValue == 0)
                    CubeConfig.createDefaultDice(false)
                else
                    CubeConfig.createUniformGhost(currentValue),
                ratio = 0.90f,
                lagFactor = 1f,
                showPips = false,
                alpha = 0.3f
            ),

            DiceLayerConfig(
                cubeConfig = if (currentValue == 0)
                    CubeConfig.createDefaultDice(false)
                else
                    CubeConfig.createUniformDice(currentValue),
                ratio = 0.75f,
                lagFactor = 1.0f,
                invertRotationX = false,
                showPips = true,
                alpha = 1.0f
            )
        )
    }

    Box(
        modifier = modifier.width(diceSize.dp).height(diceSize.dp),
        contentAlignment = Alignment.Center
    ) {
        NestedInteractiveDice(
            animationConfig = currentAnimConfig,
            onAnimationStateChange = { newConfig ->
                currentAnimConfig = newConfig
            },
            onValueChange = { newValue ->
                currentValue = newValue
            },
            layers = layers,
            size = diceSize,
            pipRadius = 0.13f,
            pipPadding = 0.05f,
            layerLocks = listOf(
                LayerLockState.unlocked(),
                LayerLockState.unlocked(),
                LayerLockState.unlocked()
            ),
            idleAnimatorActivated = false
        )
    }
}



@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun DiceItemPreview(modifier: Modifier = Modifier){

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ){
        DiceItem(
            value = 5,
            animationConfig = DiceAnimationConfig.idle(0),
            diceSize = 200f
        )

    }
}