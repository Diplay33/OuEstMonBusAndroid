package view.more_view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun MoreViewMain(navController: NavController) {
    Scaffold(topBar = { MoreTopBar() } ) {
        LazyColumn {
            item {
                MoreViewContactGroup()
            }
        }
    }
}