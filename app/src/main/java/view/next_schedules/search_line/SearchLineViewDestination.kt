package view.next_schedules.search_line

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import model.DTO.AllerDestinations
import model.DTO.DestinationsRetour
import model.DTO.Line
import model.DTO.Path
import model.DTO.RetourDestinations

@Composable
fun SearchLineViewDestination(paths: List<Path>, line: Line, navController: NavController) {
    val destinations = remember {
        mutableStateListOf<List<String>>()
    }

    LaunchedEffect(paths) {
        if(paths.first().direction == "ALLER") {
            AllerDestinations.getListOfDestinations(line.id) {
                destinations.addAll(it)
            }
        }
        else {
            RetourDestinations.getListOfDestinations(line.id) {
                destinations.addAll(it)
            }
        }
    }

    SearchLineViewDestinationRow(
        paths = paths,
        destinations = destinations,
        lineId = line.id.toString(),
        navController = navController
    )

    Spacer(modifier = Modifier
        .height(15.dp)
    )
}