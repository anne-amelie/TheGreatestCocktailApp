package fr.isen.anneamelie.thegreatestcocktailapp.models

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

class AppBarState(
    val title: String = "",
    val actions: (@Composable RowScope.() -> Unit)? = null,
    val onBackClick: (() -> Unit)? = null
)