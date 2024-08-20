package com.kisia.pets_word

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kisia.pets_word.ui.theme.Pets_wordTheme

@Composable
fun Main(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.main),
        contentDescription = "main",
        modifier = modifier
            .requiredWidth(width = 360.dp)
            .requiredHeight(height = 800.dp))
}

@Preview(widthDp = 360, heightDp = 800)
@Composable
private fun MainPreview() {
    Main(Modifier)
}