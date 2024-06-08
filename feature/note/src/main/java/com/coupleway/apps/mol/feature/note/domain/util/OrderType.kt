package com.coupleway.apps.mol.feature.note.domain.util

/**
 * Created by Hikmet Qedirov on 25.03.2024.
 */
sealed interface OrderType {
    data object Ascending : OrderType
    data object Descending : OrderType
}