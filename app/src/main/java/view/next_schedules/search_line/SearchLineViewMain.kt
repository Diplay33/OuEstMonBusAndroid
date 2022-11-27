package view.next_schedules.search_line

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
        Column(modifier = Modifier
            .padding(padding)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
        ) {
            linesByGroup.forEach { lines ->
                SearchLineViewGroup(
                    lines = lines,
                    isFavorite = linesByGroup[0].containsAll(lines) && linesByGroup[0].isNotEmpty()
                )
            }
        }
    }
}