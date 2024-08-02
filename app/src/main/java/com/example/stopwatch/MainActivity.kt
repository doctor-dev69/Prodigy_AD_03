package com.example.stopwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stopwatch.ui.theme.StopWatchTheme
import kotlinx.coroutines.delay
import kotlin.time.ExperimentalTime

class MainActivity : ComponentActivity() {

    private val viewModel = WatchViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StopWatchTheme {
                StopwatchApp()
            }
        }
    }
}

@Composable
fun StopwatchApp() {
    var time by remember { mutableStateOf(0L) }
    var lastTime by remember { mutableStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(isRunning) {
        while (isRunning) {
            lastTime = System.currentTimeMillis()
            delay(10)
            val currentTime = System.currentTimeMillis()
            time += currentTime - lastTime
            lastTime = currentTime
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            TimeDisplay(time)
            Spacer(modifier = Modifier.height(32.dp))
            ButtonRow(
                isRunning = isRunning,
                isPaused = isPaused,
                onStart = { isRunning = true },
                onPause = { isRunning = false; isPaused = true },
                onRestart = { isRunning = true },
                onReset = {
                    isRunning = false
                    time = 0L
                    isPaused = false
                }
            )
        }
    }
}

@Composable
fun TimeDisplay(time: Long) {
    val minutes = (time / 1000) / 60
    val seconds = (time / 1000) % 60
    val milliseconds = (time % 1000) / 10

    Card(
        modifier = Modifier
            .size(height = 200.dp, width = 200.dp)
            .clip(RoundedCornerShape(100.dp)),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
            contentColor = MaterialTheme.colorScheme.primary,
            Color.Cyan,
            Color.Red
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds),
                fontSize = 48.sp,
                textAlign = TextAlign.Center,
            )
        }

    }

}

@Composable
fun ButtonRow(
    isRunning: Boolean,
    isPaused: Boolean,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onRestart: () -> Unit,
    onReset: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        if (!isRunning && !isPaused) {
            Button(onClick = onStart) {
                Text("Start")
            }
        }
        if (isRunning) {
            Button(onClick = onPause) {
                Text("Pause")
            }
        }
        if (!isRunning && isPaused) {
            Button(onClick = onRestart) {
                Text("Resume")
            }
        }
        Button(onClick = onReset) {
            Text("Reset")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StopWatchPrev() {
    StopWatchTheme(darkTheme = true) {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            StopwatchApp()
        }

    }

}