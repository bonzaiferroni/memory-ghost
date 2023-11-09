package com.bollwerks.memoryghost.utils.ezchime

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool

class ChimePlayer(
    context: Context,
    vararg soundResourceIds: Int
) {
    private val audioAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_MEDIA)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()

    private val soundPool = SoundPool.Builder()
        .setMaxStreams(1)
        .setAudioAttributes(audioAttributes)
        .build()

    private val resourceMap: Map<Int, Int>

    init {
        resourceMap = soundResourceIds.associateWith {
            soundPool.load(context, it, 1)
        }
    }

    fun play(soundResourceId: Int) {
        val soundId = resourceMap[soundResourceId] ?: return
        soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
    }

    fun dispose() {
        soundPool.release()
    }
}