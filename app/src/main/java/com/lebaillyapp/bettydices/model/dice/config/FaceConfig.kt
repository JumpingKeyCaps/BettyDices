package com.lebaillyapp.bettydices.model.dice.config

import androidx.compose.ui.graphics.Color
import com.lebaillyapp.bettydices.model.dice.Pip

data class FaceConfig(
    val indices: List<Int>,
    val color: Color,
    val pips: List<Pip> = emptyList(),
)