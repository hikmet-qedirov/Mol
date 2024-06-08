package com.coupleway.apps.mol.core.ui.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/**
 * Created by Hikmet Qedirov on 04.04.2024.
 */
sealed interface UiText {
    data class DynamicString(val value: String) : UiText
    class StringResource(@StringRes val resId: Int) : UiText
    class StringResourceWithArgs(@StringRes val resId: Int, vararg val args: Any) : UiText

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(resId)
            is StringResourceWithArgs -> stringResource(resId, *args)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId)
            is StringResourceWithArgs -> context.getString(resId, *args)
        }
    }
}