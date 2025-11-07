package com.lebaillyapp.bettydices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lebaillyapp.bettydices.ui.composable.SpriteButtonConstraintPreview
import com.lebaillyapp.bettydices.ui.composable.SpriteButtonPreview
import com.lebaillyapp.bettydices.ui.theme.BettyDicesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BettyDicesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SpriteButtonConstraintPreview( modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
