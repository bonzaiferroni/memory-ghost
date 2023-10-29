package com.bollwerks.memoryghost.ui.common

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bollwerks.memoryghost.utils.Paddings

@Composable
fun AppDialog(
    showDialog: Boolean,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    if (!showDialog) return

    Dialog(
        onDismissRequest = { onDismiss.invoke() },
    ) {
        Card(
            modifier = modifier
                .padding(Paddings.small()),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
            shape = RoundedCornerShape(16.dp),
        ) {
            content()
        }
    }
}