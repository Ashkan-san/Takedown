package org.example.project

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import data.Turnier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val turnierList = remember { mutableStateListOf<Turnier>() }

            LaunchedEffect(true) {
                withContext(Dispatchers.IO) {
                    turnierList.addAll(fetchAllTurniere())
                }
            }

            App(turnierList)
        }
    }
}

/*
@Preview
@Composable
fun AppAndroidPreview() {
    App()
}*/
