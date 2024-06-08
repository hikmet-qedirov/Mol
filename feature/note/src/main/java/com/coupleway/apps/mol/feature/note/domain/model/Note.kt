package com.coupleway.apps.mol.feature.note.domain.model

import androidx.annotation.ColorInt
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coupleway.apps.mol.core.ui.theme.Pink40
import com.coupleway.apps.mol.core.ui.theme.Pink80
import com.coupleway.apps.mol.core.ui.theme.Purple40
import com.coupleway.apps.mol.core.ui.theme.Purple80
import com.coupleway.apps.mol.core.ui.theme.PurpleGrey40
import com.coupleway.apps.mol.core.ui.theme.PurpleGrey80

/**
 * Created by Hikmet Qedirov on 25.03.2024.
 */

@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String = "",
    val timestamp: Long,
    @ColorInt val color: Int
) {

    companion object {
        val noteColors = listOf(
            Purple80,
            Pink80,
            PurpleGrey80,
            Purple40,
            PurpleGrey40,
            Pink40
        )
    }
}

class InvalidNoteException(message: String) : Exception(message)