package com.lebaillyapp.bettydices.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lebaillyapp.bettydices.model.dice.config.DiceAnimationConfig
import com.lebaillyapp.bettydices.model.score.DiceScoreResult
import com.lebaillyapp.bettydices.model.score.DiceScorer
import com.lebaillyapp.bettydices.ui.composition.HexDiceSection
import com.lebaillyapp.bettydices.ui.composition.SpriteButtonsSection
import kotlin.random.Random

@Composable
fun GameScreen(){



    //Rolls & score --------------------------------
    val numberOfDice = 6
    var diceAnimConfigs by remember { mutableStateOf(List(numberOfDice) { DiceAnimationConfig.idle(0) }) }
    var diceValues by remember { mutableStateOf(List(numberOfDice) { 0 }) }
    var turnCounter by remember { mutableStateOf(50) }
    val scorer = remember { DiceScorer() }
    var lastScoreResult by remember { mutableStateOf<DiceScoreResult?>(null) }

    // Fonction de lancement unique
    val rollDiceAction = {
        turnCounter--
        val newDiceValues = List(numberOfDice) { Random.nextInt(1, 7) }
        diceValues = newDiceValues
        diceAnimConfigs = newDiceValues.mapIndexed { i, value ->
            DiceAnimationConfig.rollTo(
                targetValue = value,
                rotationsX = 10f,
                rotationsY = 10f,
                rollingDuration = 4000L,
                diceTicker = turnCounter
            )
        }
        lastScoreResult = scorer.evaluate(newDiceValues)
    }

    //----------------------------------------------




    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .systemBarsPadding()
    ) {

        // 1. Appel de HexDiceSection avec les données d'état
        HexDiceSection(
            numberOfDice = numberOfDice,
            diceValues = diceValues,
            diceAnimConfigs = diceAnimConfigs,
            lastScoreResult = lastScoreResult
        )


        SpriteButtonsSection(
            modifier = Modifier.padding(bottom = 10.dp).align(Alignment.BottomCenter),
            sizerButt = 90.dp,
            upSpacing = 10.dp,
            interSpacing = 5.dp,
            innerVerticalPadding = 16.dp,
            onRollClick = rollDiceAction,
            onUpRollClick = {},
            onBankClick = {},
            onStackClick = {},
            onBetClick = {},
            gameTurn = turnCounter,
            buttonBankIsEnabled = true,
            buttonStackIsEnabled = true,
            buttonBetIsEnabled = true,
            buttonRollIsEnabled = true,
            buttonUpRollIsEnabled = false
        )


    }
}