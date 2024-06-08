package com.coupleway.apps.mol.core.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.coupleway.apps.mol.core.ui.theme.MoLTheme

/**
 * Created by Hikmet Qedirov on 31.03.2024.
 */

@Composable
fun NoteAlertDialog(
    title: String,
    text: String,
    dismissButtonText: String,
    confirmButtonText: String,
    onConfirmClicked: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = title)
        },
        text = {
            Text(text = text)
        },
        dismissButton = {
            Text(text = dismissButtonText, modifier = Modifier.clickable {
                onDismissRequest()
            })
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            Text(text = confirmButtonText,
                modifier = Modifier.clickable {
                    onConfirmClicked()
                })
        }
    )
}

@Preview
@Composable
fun PreviewAlertDialog() {
    MoLTheme {
        NoteAlertDialog(
            "title",
            "description",
            dismissButtonText = "dismiss",
            confirmButtonText = "confirm",
            onConfirmClicked = {},
            onDismissRequest = {}
        )
    }
}
