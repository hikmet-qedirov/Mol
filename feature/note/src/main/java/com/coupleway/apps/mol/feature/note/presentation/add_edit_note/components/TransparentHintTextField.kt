package com.coupleway.apps.mol.feature.note.presentation.add_edit_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.coupleway.apps.mol.core.ui.theme.MoLTheme

/**
 * Created by Hikmet Qedirov on 25.03.2024.
 */

@Composable
fun TransparentHintTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val textModifier = if (singleLine) {
        Modifier.fillMaxWidth()
    } else Modifier.fillMaxSize()

    val localFocusManager = LocalFocusManager.current

    val keyboardOptions = if (singleLine) {
        KeyboardOptions(imeAction = ImeAction.Next)
    } else KeyboardOptions(imeAction = ImeAction.Done)

    val keyboardActions = if (singleLine) {
        KeyboardActions(
            onNext = { localFocusManager.moveFocus(FocusDirection.Next) }
        )
    } else {
        KeyboardActions(
            onDone = { keyboardController?.hide() }
        )
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            modifier = textModifier
                .onFocusChanged {
                    onFocusChange(it)
                }
        )
        if (isHintVisible) {
            Text(text = hint, style = textStyle, color = Color.Black)
        }
    }

}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewTransparentHintTextField() {
    com.coupleway.apps.mol.core.ui.theme.MoLTheme {
        TransparentHintTextField(
            text = "Example",
            hint = "enter text..",
            isHintVisible = false,
            onValueChange = {},
            singleLine = false,
            onFocusChange = {}
        )
    }
}