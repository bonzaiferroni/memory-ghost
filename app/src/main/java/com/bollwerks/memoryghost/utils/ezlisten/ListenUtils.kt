package com.bollwerks.memoryghost.utils.ezlisten

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember


@Composable
fun rememberEzListener(context: Context): EzListener {
    val ezListener = remember { EzListener(context) }

    DisposableEffect(key1 = context) {
        onDispose {
            ezListener.destroy()
        }
    }
    return ezListener
}