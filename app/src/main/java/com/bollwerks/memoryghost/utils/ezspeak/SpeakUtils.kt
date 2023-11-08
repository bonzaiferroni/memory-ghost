package com.bollwerks.memoryghost.utils.ezspeak

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class EzSpeaker(
    context: Context
) {
    private val tts: TextToSpeech = TextToSpeech(context, this::setStatus)

    private fun setStatus(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.getDefault()
        }
    }

    fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    fun shutdown() {
        tts.stop()
        tts.shutdown()
    }
}