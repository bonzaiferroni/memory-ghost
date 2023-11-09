package com.bollwerks.memoryghost.utils.ezspeak

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberEzSpeaker(
    context: Context,
    onDoneSpeaking: () -> Unit,
): EzSpeaker {
    val speaker = remember { EzSpeaker(context, onDoneSpeaking) }

    DisposableEffect(context) {
        onDispose {
            speaker.shutdown()
        }
    }
    return speaker
}

@Composable
fun TextToSpeechExample() {
    val context = LocalContext.current
    var text by remember { mutableStateOf("") }
    val speaker = rememberEzSpeaker(context, {})

    Column {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
        )
        Button(onClick = {
            speaker.speak(text)
        }) {
            Text("Speak")
        }
    }
}