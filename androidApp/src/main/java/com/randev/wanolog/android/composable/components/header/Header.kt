package com.randev.wanolog.android.composable.components.header

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.randev.movieapp_kmm.android.composable.style.MovieAppTheme

/**
 * @author Raihan Arman
 * @date 17/11/22
 */

@Composable
fun Header(
    modifier: Modifier = Modifier,
    text: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 20.sp,
            style = MovieAppTheme.typography.bold,
        )
    }
}