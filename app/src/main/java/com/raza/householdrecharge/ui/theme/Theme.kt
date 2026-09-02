package com.raza.householdrecharge.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = DarkBackgroundColor,
    primaryFixed = DarkLogoColor
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = LightBackgroundColor,
    primaryFixed = LightLogoColor
)

private val DarkColorScheme2 = darkColorScheme(
    primary = DarkLogoColor,
    onPrimary = DarkBackgroundColor,
    onSurfaceVariant = DarkLogoColor
)

private val LightColorScheme2 = lightColorScheme(
    primary = LightLogoColor,
    onPrimary = LightBackgroundColor,
    onSurfaceVariant = LightLogoColor
)

@Composable
fun HouseholdRechargeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme: ColorScheme

    if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && darkTheme) {
        colorScheme = dynamicDarkColorScheme(context)
    }
    else if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        colorScheme = dynamicLightColorScheme(context)
    }
    else if (darkTheme) {
        colorScheme = DarkColorScheme2
    }
    else {
        colorScheme = LightColorScheme2
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}