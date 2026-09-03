package com.raza.householdrecharge.common

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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

@Composable
fun AppCard(
    padding: Dp = 20.dp,
    elevation: Dp = 8.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = padding),

        elevation = elevation,

        content = content
    )
}

@Composable
fun AppCard(
    paddingValues: PaddingValues,
    elevation: Dp = 8.dp,
    cornerSize: Dp = 4.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues = paddingValues),
        elevation = elevation,
        cornerSize = cornerSize,
        content = content
    )
}

@Composable
fun AppCard(
    modifier: Modifier = Modifier.fillMaxWidth(),
    elevation: Dp = 8.dp,
    cornerSize: Dp = 4.dp,
    content: @Composable ColumnScope.() -> Unit
) {

    Card(
        modifier = modifier,

        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation
        ),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),

        shape = RoundedCornerShape(size = cornerSize),

        content = content
    )
}