package com.bollwerks.memoryghost.utils.ezspeak

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.text.intl.Locale

@Composable
fun rememberSpeechRecognizer(context: Context): SpeechRecognizer {
    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }

    DisposableEffect(key1 = context) {
        onDispose {
            speechRecognizer?.destroy()
        }
    }
    return speechRecognizer
}

fun SpeechRecognizer.startListening() {
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.current.toString())
        putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...")
    }
    this.startListening(intent)
}

fun SpeechRecognizer.onResults(
    onResults: (String) -> Unit,
    onError: () -> Unit = {},
) {
    this.setRecognitionListener(EzSpeakListener(onResults, onError))
}

class EzSpeakListener(
    private val onResults: (String) -> Unit,
    private val onError: () -> Unit,
) : RecognitionListener {

    override fun onResults(results: Bundle?) {
        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        if (!matches.isNullOrEmpty()) {
            onResults(matches[0])
        }
    }

    // Implement other necessary methods of RecognitionListener interface
    override fun onReadyForSpeech(params: Bundle?) {}
    override fun onBeginningOfSpeech() {}
    override fun onRmsChanged(rmsdB: Float) {}
    override fun onBufferReceived(buffer: ByteArray?) {}
    override fun onEndOfSpeech() {}

    override fun onError(error: Int) {
        onError()
    }

    override fun onPartialResults(partialResults: Bundle?) {}
    override fun onEvent(eventType: Int, params: Bundle?) {}
}