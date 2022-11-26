package view.next_schedules

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun NextSchedulesHomeViewMain(navController: NavController) {
    Scaffold(topBar = { NextSchedulesHomeViewTopBar() }) { padding ->
        LazyColumn(modifier = Modifier
            .padding(padding)
        ) {
            item {
                NextSchedulesHomeViewSearchGroup(navController)

                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                NextSchedulesHomeViewFavoritesGroup()
            }
        }
    }
}