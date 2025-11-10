package com.lebaillyapp.bettydices.ui.composition


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import com.lebaillyapp.bettydices.R

import androidx.constraintlayout.compose.ConstraintLayout
import com.lebaillyapp.bettydices.ui.composable.SpriteButton

@Composable
fun SpriteButtonsSection(
    modifier: Modifier = Modifier,
    sizerButt: Dp = 90.dp,
    upSpacing: Dp = 10.dp,
    interSpacing: Dp = 5.dp,
    innerVerticalPadding: Dp = 16.dp,
    onRollClick: () -> Unit,
    onUpRollClick: () -> Unit,
    onBankClick: () -> Unit,
    onStackClick: () -> Unit,
    onBetClick: () -> Unit,
    gameTurn: Int = 50,
    buttonBankIsEnabled: Boolean = true,
    buttonStackIsEnabled: Boolean = true,
    buttonBetIsEnabled: Boolean = true,
    buttonRollIsEnabled: Boolean = true,
    buttonUpRollIsEnabled: Boolean = false
    ) {


    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black),
        contentAlignment = Alignment.BottomCenter
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = innerVerticalPadding, top = innerVerticalPadding)
        ) {
            // refs pour les boutons
            val (btn1, btn2, btn3, btn4, btnTop) = createRefs()
            // refs pour les spacers
            val (spacer1, spacer2, spacer3) = createRefs()

            // 1 - Bank
            SpriteButton(
                modifier = Modifier.constrainAs(btn1) { bottom.linkTo(parent.bottom) },
                resId = R.drawable.cyb_but_scoreur,
                text = null,
                onClick = onBankClick,
                size = sizerButt,
                fontRes = R.font.micro_regular,
                enabled = buttonBankIsEnabled
            )
            // Spacer 1
            Spacer(modifier = Modifier
                .width(interSpacing)
                .constrainAs(spacer1) { bottom.linkTo(btn1.bottom) })

            // 2 - Stack
            SpriteButton(
                modifier = Modifier.constrainAs(btn2) { bottom.linkTo(btn1.bottom) },
                resId = R.drawable.cyb_but_bankbag,
                text = "4136",
                textSize = 30.sp,
                onClick = onStackClick,
                size = sizerButt,
                fontRes = R.font.micro_regular,
                enabled = buttonStackIsEnabled
            )
            // Spacer 2
            Spacer(modifier = Modifier
                .width(interSpacing)
                .constrainAs(spacer2) { bottom.linkTo(btn2.bottom) })

            // 3 - Bet
            SpriteButton(
                modifier = Modifier.constrainAs(btn3) { bottom.linkTo(btn1.bottom) },
                resId = R.drawable.cyb_but_bet,
                onClick = onBetClick,
                size = sizerButt,
                textSize = 30.sp,
                fontRes = R.font.micro_regular,
                enabled = buttonBetIsEnabled
            )
            // Spacer 3
            Spacer(modifier = Modifier
                .width(interSpacing)
                .constrainAs(spacer3) { bottom.linkTo(btn3.bottom) })

            // 4 - Roll
            SpriteButton(
                modifier = Modifier.constrainAs(btn4) { bottom.linkTo(btn1.bottom) },
                resId = R.drawable.cyb_but_dices,
                text = gameTurn.toString(),
                textSize = 30.sp,
                vibronicType = 999,
                onClick = onRollClick,
                size = sizerButt,
                fontRes = R.font.micro_regular,
                enabled = buttonRollIsEnabled
            )

            // 5- Up Roll!
            SpriteButton(
                modifier = Modifier.constrainAs(btnTop) {
                    bottom.linkTo(btn4.top, margin = upSpacing)
                    start.linkTo(btn4.start)
                    end.linkTo(btn4.end)
                },
                resId = R.drawable.cyb_but_uproll,
                vibronicType = 999,
                onClick = onUpRollClick,
                size = sizerButt,
                fontRes = R.font.micro_regular,
                enabled = buttonUpRollIsEnabled
            )

            // Chaîne horizontale centrée avec spacers
            createHorizontalChain(
                btn1, spacer1, btn2, spacer2, btn3, spacer3, btn4,
                chainStyle = ChainStyle.Packed
            )
        }
    }
}



@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun SpriteButtonsSectionPreview(modifier: Modifier = Modifier){

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ){
        SpriteButtonsSection(
            modifier = Modifier.padding(bottom = 10.dp).align(Alignment.BottomCenter),
            sizerButt = 90.dp,
            upSpacing = 10.dp,
            interSpacing = 5.dp,
            innerVerticalPadding = 16.dp,
            onRollClick = {},
            onUpRollClick = {},
            onBankClick = {},
            onStackClick = {},
            onBetClick = {}
        )

    }
}