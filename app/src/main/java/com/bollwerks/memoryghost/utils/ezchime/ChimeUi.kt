package com.bollwerks.memoryghost.utils.ezchime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberChimePlayer(
    vararg soundResourceIds: Int
): ChimePlayer {
    val context = LocalContext.current
    val player = remember { ChimePlayer(context, *soundResourceIds) }

    // Dispose of the SoundPool
    DisposableEffect(Unit) {
        onDispose {
            player.dispose()
        }
    }
    return player
}