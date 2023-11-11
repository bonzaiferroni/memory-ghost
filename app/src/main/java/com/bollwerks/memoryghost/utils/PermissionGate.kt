package com.bollwerks.memoryghost.utils

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun PermissionGate(
    permission: String,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    var isPermissionGranted by remember { mutableStateOf(false) }
    var textToShow by remember { mutableStateOf("Click to check permission") }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            isPermissionGranted = isGranted
            textToShow = if (isGranted) {
                "Permission Granted"
            } else {
                "Permission Denied. Please grant permission to use this feature."
            }
        }
    )

    LaunchedEffect(Unit) {
        if (context.checkSelfPermission(permission) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            isPermissionGranted = true
            textToShow = "Permission already granted"
        }
    }

    if (isPermissionGranted) {
        content()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Button(onClick = {
                if (!isPermissionGranted) {
                    permissionLauncher.launch(permission)
                }
            }) {
                Text(textToShow)
            }
        }
    }
}

// works with:
// <uses-permission android:name="android.permission.RECORD_AUDIO" />
// <uses-permission android:name="android.permission.INTERNET" />
// <uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />