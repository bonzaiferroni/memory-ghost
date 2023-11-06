package com.bollwerks.memoryghost.utils

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp

@Composable
fun SpeechInputField() {
    var text by remember { mutableStateOf("") }
    val context = LocalContext.current
    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }
    val isListening = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = isListening.value) {
        if (isListening.value) {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.current.toString())
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...")
            }
            speechRecognizer.startListening(intent)
        }
    }

    DisposableEffect(key1 = context) {
        onDispose {
            speechRecognizer.destroy()
        }
    }

    speechRecognizer.setRecognitionListener(MyListener {
        text = it
        isListening.value = false
    })

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        isListening.value = true
                        // Hide the keyboard if it's shown
                        focusRequester.freeFocus()
                    }
                },
            label = { Text("Speak") },
            trailingIcon = {
                IconButton(onClick = { isListening.value = true }) {
                    Icon(Icons.Default.AddCircle, contentDescription = "Speak")
                }
            },
            readOnly = isListening.value,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.None
            )
        )
    }
}

class MyListener(private val onFinishedListening: (String) -> Unit) : RecognitionListener {

    override fun onResults(results: Bundle?) {
        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        if (!matches.isNullOrEmpty()) {
            onFinishedListening(matches[0])
        }
    }

    // Implement other necessary methods of RecognitionListener interface
    override fun onReadyForSpeech(params: Bundle?) {}
    override fun onBeginningOfSpeech() {}
    override fun onRmsChanged(rmsdB: Float) {}
    override fun onBufferReceived(buffer: ByteArray?) {}
    override fun onEndOfSpeech() {}
    override fun onError(error: Int) {}
    override fun onPartialResults(partialResults: Bundle?) {}
    override fun onEvent(eventType: Int, params: Bundle?) {}
}