package com.bollwerks.memoryghost.utils.ezlisten

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ListenInputField(
    value: String,
    modifier: Modifier = Modifier,
    label: String = "Speak",
    onValueChange: (String) -> Unit,
    isPreview: Boolean = false,
) {
    val speechRecognizer = if (isPreview) null else rememberSpeechRecognizer(LocalContext.current)
    val isListening = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    speechRecognizer?.onResults(
        onResults = { text ->
            onValueChange(text)
            isListening.value = false
        },
        onError = {
            isListening.value = false
        }
    )

    LaunchedEffect(key1 = isListening.value) {
        if (isListening.value) {
            speechRecognizer?.startListening()
        }
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    // Hide the keyboard if it's shown
                    focusRequester.freeFocus()
                }
            },
        label = { Text(label) },
        trailingIcon = {
            IconButton(onClick = { isListening.value = true }) {
                Text("🎤")
            }
        },
        readOnly = isListening.value,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.None
        )
    )
}

@Preview
@Composable
fun SpeechInputFieldPreview() {
    ListenInputField(
        value = "Hello",
        onValueChange = {},
        isPreview = true,
    )
}