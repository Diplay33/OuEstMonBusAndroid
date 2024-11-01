package view.lines_map_list

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.diplay.ouestmonbus.BuildConfig
import model.DTO.Line
import model.DTO.ProgrammedMessage
import model.DTO.Service
import view.advert_view.AdvertView

@Composable
fun LinesMapListGroup(
    lines: List<Line>,
    isFavorite: Boolean,
    linesByGroup: MutableState<MutableList<List<Line>>>,
    navController: NavController,
    services: MutableList<Service>,
    isLoading: MutableState<Boolean>,
    programmedMessages: List<ProgrammedMessage>
) {
    val colorScheme = !isSystemInDarkTheme()

    Column(modifier = Modifier
        .padding(vertical = if(lines.isEmpty()) 0.dp else 10.dp)
    ) {
        if(isFavorite && lines.isNotEmpty()) {
            Text(
                text = "Favoris",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = if (colorScheme) Color.Black else Color.White,
                modifier = Modifier
                    .padding(start = 15.dp)
            )
        }

        lines.forEach { line ->
            if(BuildConfig.DEBUG) {
                LinesMapListRow(
                    rowLine = line,
                    linesByGroup = linesByGroup,
                    navController = navController,
                    services = services,
                    isLoading = isLoading,
                    programmedMessagesCount = programmedMessages.filter { it.lineId == line.id }.size,
                    clickRowLine = true
                )
            }
            else {
                Column {
                    LinesMapListRow(
                        rowLine = line,
                        linesByGroup = linesByGroup,
                        navController = navController,
                        services = services,
                        isLoading = isLoading,
                        programmedMessagesCount = programmedMessages.filter { it.lineId == line.id }.size,
                        clickRowLine = true
                    )

                    if(line == lines.last() && lines != linesByGroup.value.last()) {
                        Spacer(modifier = Modifier
                            .size(25.dp)
                        )

                        AdvertView(modifier = Modifier
                            .height(50.dp)
                        )
                    }
                }
            }
        }
    }
}