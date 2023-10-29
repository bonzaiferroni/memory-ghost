package com.bollwerks.memoryghost.ui.common

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun ValueField(
    value: Float?,
    modifier: Modifier = Modifier,
    label: String = "Number",
    imeAction: ImeAction = ImeAction.Next,
    onValueChange: (Float) -> Unit,
) {
    var text by remember { mutableStateOf(value?.toString() ?: "") }

    if (value != null && !text.endsWith('.')) {
        val floatValue = text.toFloatOrNull()
        if (value != floatValue) {
            text = value.toString()
        }
    }

    val onTextChanged: (String) -> Unit = { updatedValue ->
        text = updatedValue
        updatedValue.toFloatOrNull()?.let { onValueChange(it) }
    }

    ValueField(
        value = text,
        label = label,
        keyboardType = KeyboardType.Decimal,
        imeAction = imeAction,
        onValueChange = onTextChanged,
        modifier = modifier,
    )
}

@Composable
fun ValueField(
    value: Int?,
    modifier: Modifier = Modifier,
    label: String = "Integer",
    imeAction: ImeAction = ImeAction.Next,
    onValueChange: (Int) -> Unit,
) {
    ValueField(
        value = value?.toString() ?: "",
        label = label,
        keyboardType = KeyboardType.Number,
        imeAction = imeAction,
        onValueChange = { text ->
            text.toIntOrNull()?.let {
                onValueChange(it)
            }
        },
        modifier = modifier,
    )
}

@Composable
fun ValueField(
    label: String,
    value: String?,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value ?: "",
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        modifier = modifier
    )
}