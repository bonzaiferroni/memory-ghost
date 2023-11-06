package com.bollwerks.memoryghost.ui.sandbox

import android.Manifest
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bollwerks.memoryghost.ui.common.AppScaffold
import com.bollwerks.memoryghost.utils.PermissionGate
import com.bollwerks.memoryghost.utils.SpeechInputField

@Composable
fun SandboxScreen(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
) {
    AppScaffold(
        modifier = modifier,
        drawerState = drawerState,
        title = "\uD83C\uDFD6\uFE0F",
    ) {
        PermissionGate(Manifest.permission.RECORD_AUDIO) {
            SpeechInputField()
        }
    }
}