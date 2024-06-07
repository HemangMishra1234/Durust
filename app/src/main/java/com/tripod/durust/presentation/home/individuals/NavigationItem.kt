package com.tripod.durust.presentation.home.individuals

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector?  = null,
    val unselectedIcon: ImageVector?= null,
    val image: Int? = null
)
