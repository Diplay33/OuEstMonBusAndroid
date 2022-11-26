package view.next_schedules.search_line

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import model.DTO.Lines

@Composable
fun SearchLineViewMain(navController: NavController) {
    val context = LocalContext.current
    val linesByGroup = Lines.getLinesByGroup(context)

    Scaffold(topBar = { SearchLineViewTopBar(navController) }) { padding ->
        LazyColumn(modifier = Modifier
            .padding(padding)
            .fillMaxWidth()
        ) {
            items(linesByGroup) { lines ->
                SearchLineViewGroup(
                    lines = lines,
                    isFavorite = linesByGroup[0].containsAll(lines) && linesByGroup[0].isNotEmpty()
                )
            }
        }
    }
}