package com.raza.householdrecharge.v2.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun LargeTitleText(modifier: Modifier = Modifier,
                   text: String,
                   color: Color = Color.Unspecified) {
    CommonText(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = color
    )
}

@Composable
fun LargeBodyText(modifier: Modifier = Modifier,
                  text: String,
                  color: Color = Color.Unspecified) {
    CommonText(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = color
    )
}

@Composable
fun LargeHeadlineText(modifier: Modifier = Modifier,
                      text: String,
                      color: Color = Color.Unspecified) {
    CommonText(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.headlineLarge,
        color = color
    )
}

@Composable
fun LargeDisplayText(modifier: Modifier = Modifier,
                     text: String,
                     color: Color = Color.Unspecified) {
    CommonText(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.displayLarge,
        color = color
    )
}

@Composable
private fun CommonText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    style: TextStyle = MaterialTheme.typography.labelLarge,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = style
    )
}
