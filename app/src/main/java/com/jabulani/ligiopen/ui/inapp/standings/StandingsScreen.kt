package com.jabulani.ligiopen.ui.inapp.standings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jabulani.ligiopen.ui.theme.LigiopenTheme

@Composable
fun StandingsScreenComposable() {
    Box(
        modifier = Modifier
            .safeDrawingPadding()
    ) {
        StandingsScreen()
    }
}

@Composable
fun StandingsScreen(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "Standings")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StandingsScreenPreview() {
    LigiopenTheme {
        StandingsScreen()
    }
}