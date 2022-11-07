package view.lines_map_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.DTO.*
import model.preferences_data_store.StoreDisplayNotifCountParam
import model.preferences_data_store.StoreFavoriteLines
import view.Screens.CartesScreens


@Composable
fun LinesMapListRow(rowLine: Line, linesByGroup: SnapshotStateList<ArrayList<Line>>, navController: NavController) {
    val servicesAreLoaded = remember {
        mutableStateOf(false)
    }
    val services = remember {
        mutableStateListOf<Service>()
    }
    val programmedMessagesCount = remember {
        mutableStateOf(0)
    }
    val menuShown = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val storeFavLines = StoreFavoriteLines(context, rowLine.id.toString())
    val storeDisplayNotifCount = StoreDisplayNotifCountParam(context)
    val isFavorite = storeFavLines.isFavorite.collectAsState(initial = false)
    val displayNotifCount = storeDisplayNotifCount.isEnabled.collectAsState(initial = false)

    LaunchedEffect(rowLine) {
        servicesAreLoaded.value = false
        ProgrammedMessages.getNumberOfMessagesByLine(rowLine.id.toString()) { messagesCount ->
            programmedMessagesCount.value = messagesCount
        }

        while(true) {
            if(rowLine.id == 132) {
                Services.getNavetteTramServices { returnedServices ->
                    servicesAreLoaded.value = true
                    services.clear()
                    services.addAll(returnedServices)
                }
            }
            else {
                Services.getServicesByLine(rowLine.id) { returnedServices ->
                    servicesAreLoaded.value = true
                    services.clear()
                    services.addAll(returnedServices)
                }
            }
            delay(20000)
        }
    }

    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .padding(vertical = 5.dp, horizontal = 15.dp)
        .background(
            colorResource(id = rowLine.lineColorResource).copy(alpha = 0.2f),
            shape = RoundedCornerShape(10.dp)
        )
        .fillMaxWidth()
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = { },
                onDoubleTap = { },
                onLongPress = { menuShown.value = true },
                onTap = { navController.navigate(CartesScreens.HelloWorld.withArgs(rowLine.id.toString())) }
            )
        }
    ) {
        Row(modifier = Modifier
            .padding(start = 15.dp)
        ) {
            Image(painterResource(id = rowLine.lineImageResource), contentDescription = null, modifier = Modifier
                .size(70.dp)
                .padding(vertical = 4.dp)
            )

            Column {
                Spacer(modifier = Modifier
                    .height(5.dp)
                )

                Text(rowLine.lineName, fontWeight = FontWeight.Bold, fontSize = 23.sp, modifier = Modifier
                    .padding(horizontal = 10.dp)
                )

                Spacer(modifier = Modifier
                    .height(2.dp)
                )

                Row(modifier = Modifier
                    .padding(horizontal = 10.dp)
                ) {
                    if(!servicesAreLoaded.value) {
                        Text("Chargement en cours...",
                            fontStyle = FontStyle.Italic,
                            color = Color.Gray
                        )
                    }
                    else {
                        ColorIndicatorDot(if (services.isEmpty()) Color.Red else Color.Green,
                            10.dp,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )

                        Spacer(modifier = Modifier
                            .width(5.dp)
                        )

                        Text(
                            if (services.isEmpty())
                                "Aucun véhicule en service"
                            else
                                if (services.size == 1)
                                    "1 véhicule en service"
                                else
                                    services.size.toString() + " véhicules en service"
                        )
                    }
                }
            }
        }

        Row(modifier = Modifier
            .fillMaxHeight()
            .align(Alignment.CenterVertically)
        ) {
            if(programmedMessagesCount.value > 0 && displayNotifCount.value == true) {
                Column(modifier = Modifier
                    .fillMaxHeight()
                ) {
                    NotificationCountBadge(count = programmedMessagesCount.value)
                }
            }

            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(start = 10.dp, end = 15.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowForward,
                    contentDescription = null,
                )
            }
        }

        DropdownMenu(
            expanded = menuShown.value,
            onDismissRequest = { menuShown.value = false }
        ) {
            if(isFavorite.value == true) {
                DropdownMenuItem(onClick = {
                    scope.launch {
                        storeFavLines.removeFromFavorites(rowLine.id.toString())
                        linesByGroup.clear()
                        linesByGroup.addAll(Lines.getLinesByGroup(context))
                        println("Removed from favorites")
                    }
                    menuShown.value = false
                }) {
                    Row {
                        Icon(imageVector = Icons.Rounded.Favorite, contentDescription = null, modifier = Modifier
                            .size(30.dp)
                        )

                        Spacer(modifier = Modifier
                            .width(5.dp)
                        )

                        Text("Retirer des favoris", modifier = Modifier
                            .align(Alignment.CenterVertically)
                        )
                    }
                }
            }
            else {
                DropdownMenuItem(onClick = {
                    scope.launch {
                        storeFavLines.saveFavoriteLine(rowLine.id.toString())
                        linesByGroup.clear()
                        linesByGroup.addAll(Lines.getLinesByGroup(context))
                        println("Added to favorites")
                    }
                    menuShown.value = false
                }) {
                    Row {
                        Icon(imageVector = Icons.Rounded.FavoriteBorder, contentDescription = null, modifier = Modifier
                            .size(30.dp)
                        )

                        Spacer(modifier = Modifier
                            .width(5.dp)
                        )

                        Text("Ajouter aux favoris", modifier = Modifier
                            .align(Alignment.CenterVertically)
                        )
                    }
                }
            }

        }
    }
}