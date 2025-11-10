package com.lebaillyapp.bettydices.ui.composition

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.lebaillyapp.bettydices.model.dice.config.DiceAnimationConfig
import com.lebaillyapp.bettydices.model.score.DiceScoreResult
import com.lebaillyapp.bettydices.model.score.DiceScorer
import com.lebaillyapp.bettydices.ui.composable.DiceItem
import kotlin.collections.chunked
import kotlin.collections.forEachIndexed
import kotlin.random.Random



@Composable
fun HexDiceSection(
    numberOfDice: Int = 6,
    diceValues: List<Int>,
    diceAnimConfigs: List<DiceAnimationConfig>,
    lastScoreResult: DiceScoreResult?
) {

    // Paramètres pour l'hexagone
    val radius = 120.dp // Distance du centre
    val startAngle = 0f // Angle de départ (met le premier dé en haut)
    val angleIncrement = 360f / numberOfDice // 60 degrés pour 6 dés
    val diceSize = 100f // Taille de vos dés (doit correspondre au DiceItem)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // --- SECTION DES DÉS AVEC CONSTRAINTLAYOUT ---
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f) // Utiliser une partie de l'écran pour les dés
                .align(Alignment.Center)
        ) {
            val center = createRef()

            // Un petit Spacer pour marquer le centre et l'ancrer au parent
            Spacer(
                modifier = Modifier
                    .size(1.dp)
                    .constrainAs(center) {
                        centerTo(parent)
                    }
            )

            // Création des références pour tous les dés
            val diceRefs = List(numberOfDice) { createRef() }

            diceValues.forEachIndexed { globalIndex, value ->
                val angle = startAngle + (globalIndex * angleIncrement)

                // Définissez le Modifier contraint et PASSEZ-LE à DiceItem
                val diceModifier = Modifier.constrainAs(diceRefs[globalIndex]) {
                    circular(center, angle, radius)
                }

                DiceItem(
                    value = value,
                    animationConfig = diceAnimConfigs[globalIndex],
                    diceSize = diceSize,
                    modifier = diceModifier
                )
            }
        }

        // --- Score ---
        lastScoreResult?.let { result ->
            Column(
                modifier = Modifier.padding(top = 26.dp).align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Score: ${result.totalScore}" + if (result.isFanny) " (Fanny!)" else "",
                    color = Color.White
                )
                if (result.combinations.isNotEmpty()) {
                    result.combinations.forEach { combo ->
                        Text(
                            text = "${combo.type}: ${combo.diceUsed.joinToString(", ")} → ${combo.score}",
                            color = Color.LightGray,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }


    }
}








//todo - LEGACY --- to remove
@Composable
fun HexDiceSectionOLD(numberOfDice: Int = 6, middleRowSpacing: Dp = 100.dp) {
    var diceAnimConfigs by remember { mutableStateOf(List(numberOfDice) { DiceAnimationConfig.idle(0) }) }
    var diceValues by remember { mutableStateOf(List(numberOfDice) { 0 }) }
    var turnCounter by remember { mutableStateOf(0) }

    val scorer = remember { DiceScorer() }
    var lastScoreResult by remember { mutableStateOf<DiceScoreResult?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier.fillMaxWidth().align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            val rows = diceValues.chunked(2)

            rows.forEachIndexed { rowIndex, rowDice ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    rowDice.forEachIndexed { indexInRow, value ->
                        val globalIndex = rowIndex * 2 + indexInRow
                        if (rowIndex == 1 && indexInRow == 1) {
                            Spacer(modifier = Modifier.width(middleRowSpacing))
                        }

                        DiceItem(
                            value = value,
                            animationConfig = diceAnimConfigs[globalIndex],
                            diceSize = 100f
                        )
                    }
                }
            }
        }

        // --- Score ---
        lastScoreResult?.let { result ->
            Column(
                modifier = Modifier.padding(top = 26.dp).align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Score: ${result.totalScore}" + if (result.isFanny) " (Fanny!)" else "",
                    color = Color.White
                )
                if (result.combinations.isNotEmpty()) {
                    result.combinations.forEach { combo ->
                        Text(
                            text = "${combo.type}: ${combo.diceUsed.joinToString(", ")} → ${combo.score}",
                            color = Color.LightGray,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // --- Bouton ---
        Button(
            onClick = {
                turnCounter++
                diceValues = List(numberOfDice) { Random.nextInt(1, 7) }
                diceAnimConfigs = diceValues.mapIndexed { i, value ->
                    DiceAnimationConfig.rollTo(
                        targetValue = value,
                        rotationsX = 10f,
                        rotationsY = 10f,
                        rollingDuration = 4000L,
                        diceTicker = turnCounter
                    )
                }
                lastScoreResult = scorer.evaluate(diceValues)
            },
            modifier = Modifier.padding(top = 100.dp, start = 25.dp, end = 25.dp, bottom = 25.dp).height(54.dp).align(Alignment.TopCenter),
            colors = ButtonDefaults.buttonColors(Color(0x77000000))
        ) {
            val currentValues = if (diceValues.all { it == 0 }) "Classic" else diceValues.joinToString(", ")
            Text("Roll Dice! (Currently: $currentValues)", color = Color.White)
        }
    }
}