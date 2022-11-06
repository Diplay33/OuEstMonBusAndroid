package view.more

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun MoreMain(navController: NavController) {
    Scaffold(topBar = { MoreTopBar() } ) {
        
    }
}