package com.raza.householdrecharge.presentation.settings

import com.raza.householdrecharge.presentation.theme.ThemeMode

data class SettingUIState(
    var themeMode: ThemeMode = ThemeMode.SYSTEM
)