package view.next_schedules.search_line

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun SearchLineViewMain(navController: NavController) {
    Scaffold(topBar = { SearchLineViewTopBar(navController) }) { padding ->
        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .padding(padding)
            .fillMaxSize()
        ) {
            Text(text = "Coming Soon...")
        }
    }
}