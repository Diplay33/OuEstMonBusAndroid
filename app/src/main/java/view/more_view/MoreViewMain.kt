package view.more_view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MoreViewMain(navController: NavController) {
    Scaffold(topBar = { MoreTopBar() } ) { padding ->
        LazyColumn(modifier = Modifier
            .padding(padding)
        ) {
            item {
                MoreViewContactGroup()

                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                MoreViewWebVersionRow()

                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                MoreViewAllServicesRow(navController)

                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                MoreViewSettingsGroup()

                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                MoreViewAppInfoGroup()
            }
        }
    }
}