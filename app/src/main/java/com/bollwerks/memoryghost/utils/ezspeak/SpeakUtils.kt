package com.bollwerks.memoryghost.utils.ezspeak

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.Locale

class EzSpeaker(
    context: Context,
    onDoneSpeaking: () -> Unit,
) {
    private val tts: TextToSpeech = TextToSpeech(context, this::setStatus)
        .apply {
            setOnUtteranceProgressListener(EzProgressListener(onDoneSpeaking))
        }

    private fun setStatus(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.getDefault()
        }
        Log.d("EzSpeaker", "setStatus: $status")
    }

    fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_ADD, null, text)
    }

    fun shutdown() {
        tts.stop()
        tts.shutdown()
    }
}

class EzProgressListener(
    val onDoneSpeaking: () -> Unit
) : UtteranceProgressListener() {
    override fun onStart(utteranceId: String?) {
        Log.d("EzProgressListener", "onStart: $utteranceId")
    }

    override fun onDone(utteranceId: String?) {
        onDoneSpeaking()
    }

    @Deprecated("Deprecated in Java")
    override fun onError(utteranceId: String?) {
        Log.d("EzProgressListener", "onError: $utteranceId")
    }
}