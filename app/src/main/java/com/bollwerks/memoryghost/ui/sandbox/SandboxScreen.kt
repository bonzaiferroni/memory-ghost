package com.bollwerks.memoryghost.ui.sandbox

import android.Manifest
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.bollwerks.eznav.EzScaffold
import com.bollwerks.memoryghost.utils.PermissionGate
import com.bollwerks.memoryghost.utils.ezspeak.SpeechInputField
import com.bollwerks.memoryghost.utils.ezspeak.rememberSpeechRecognizer

@Composable
fun SandboxScreen(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
) {
    EzScaffold(
        modifier = modifier,
        drawerState = drawerState,
        title = "\uD83C\uDFD6\uFE0F",
    ) {
        PermissionGate(Manifest.permission.RECORD_AUDIO) {
            var text by remember { mutableStateOf("") }
            SpeechInputField(
                value = text,
                onValueChange = { text = it },
            )
        }
    }
}