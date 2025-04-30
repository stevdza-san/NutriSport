package com.nutrisport.shared.domain

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class QuantityCounterSize(
    val spacing: Dp,
    val padding: Dp
) {
    Small(
        spacing = 4.dp,
        padding = 8.dp
    ),
    Large(
        spacing = 8.dp,
        padding = 12.dp
    )
}