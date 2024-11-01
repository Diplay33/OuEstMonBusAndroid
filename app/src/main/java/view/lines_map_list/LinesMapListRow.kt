package view.lines_map_list

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.DTO.*
import model.preferences_data_store.StoreDisplayNotifCountParam
import model.preferences_data_store.StoreFavoriteLines
import view.Screens.CartesScreens


@Composable
fun LinesMapListRow(
    rowLine: Line,
    linesByGroup: MutableState<MutableList<List<Line>>>,
    navController: NavController,
    services: MutableList<Service>,
    isLoading: MutableState<Boolean>,
    programmedMessagesCount: Int,
    clickRowLine: Boolean,
    index: Int = 0,
    searchText: MutableState<String> = mutableStateOf("")
) {
    val serviceCount = services.filter { it.lineId == rowLine.id }.size
    val nestServicesCount = remember {
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
    val colorScheme = !isSystemInDarkTheme()

    LaunchedEffect(isLoading.value) {
        if(rowLine.isNest) {
            Lines.getChildLineIds(rowLine.id) { childLineIds ->
                nestServicesCount.value = services.filter { childLineIds.contains(it.lineId) }.size
            }
        }
    }

    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .padding(vertical = 5.dp, horizontal = 15.dp)
        .background(
            if (colorScheme) Color.Transparent else Color(0xff18191A),
            shape = RoundedCornerShape(10.dp)
        )
        .background(
            Color(android.graphics.Color.parseColor(rowLine.colorHex)).copy(alpha = 0.2f),
            shape = RoundedCornerShape(10.dp)
        )
        .fillMaxWidth()
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = { },
                onDoubleTap = { },
                onLongPress = { menuShown.value = true },
                onTap = {
                    if(clickRowLine) {
                        navController.navigate(CartesScreens.HelloWorld.withArgs(rowLine.id.toString()))
                    }
                    else {
                        Lines.getLinesBySearchText(searchText.value) {
                            scope.launch {
                                navController.navigate(CartesScreens.HelloWorld.withArgs(it[index].id.toString()))
                            }
                        }
                    }
                }
            )
        }
    ) {
        Row(modifier = Modifier
            .padding(start = 15.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(rowLine.imageUrl)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .padding(vertical = 4.dp)
            )

            Column(modifier = Modifier
                .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = rowLine.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 23.sp,
                    color = if (colorScheme) Color.Black else Color.White,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                )

                Spacer(modifier = Modifier
                    .height(4.dp)
                )

                Row(modifier = Modifier
                    .padding(horizontal = 10.dp)
                ) {
                    if(isLoading.value) {
                        Text("Chargement en cours...",
                            fontStyle = FontStyle.Italic,
                            color = Color.Gray
                        )
                    }
                    else {
                        ColorIndicatorDot(
                            color = if ((if (rowLine.isNest)
                                nestServicesCount.value
                                else
                                    serviceCount) == 0) Color.Red else Color.Green,
                            size = 10.dp,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )

                        Spacer(modifier = Modifier
                            .width(5.dp)
                        )

                        Text(
                            when(if (rowLine.isNest)
                                nestServicesCount.value
                            else
                                serviceCount) {
                                0 -> "Aucun véhicule en service"
                                1 -> "1 véhicule en service"
                                else -> "${if (rowLine.isNest)
                                    nestServicesCount.value 
                                else 
                                    serviceCount} véhicules en service"
                            },
                            color = if (colorScheme) Color.Black else Color.White
                        )
                    }
                }
            }
        }

        Row(modifier = Modifier
            .fillMaxHeight()
            .align(Alignment.CenterVertically)
        ) {
            if(programmedMessagesCount > 0 && displayNotifCount.value == true) {
                Column(modifier = Modifier
                    .fillMaxHeight()
                ) {
                    NotificationCountBadge(count = programmedMessagesCount)
                }
            }

            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(start = 10.dp, end = 15.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowForward,
                    contentDescription = null,
                    tint = if (colorScheme) Color.Black else Color.White
                )
            }
        }

        DropdownMenu(
            expanded = menuShown.value,
            onDismissRequest = { menuShown.value = false },
            modifier = Modifier
                .background(if (colorScheme) Color.White else Color(0xff18191A))
        ) {
            if(isFavorite.value == true) {
                DropdownMenuItem(onClick = {
                    scope.launch {
                        storeFavLines.removeFromFavorites(rowLine.id.toString())
                        linesByGroup.value.clear()
                        linesByGroup.value.addAll(Lines.getAllLinesBySection(context))
                    }
                    menuShown.value = false
                }) {
                    Row {
                        Icon(
                            imageVector = Icons.Rounded.Favorite,
                            contentDescription = null,
                            tint = if (colorScheme) Color.Black else Color.White,
                            modifier = Modifier
                                .size(30.dp)
                        )

                        Spacer(modifier = Modifier
                            .width(5.dp)
                        )

                        Text(
                            text = "Retirer des favoris",
                            color = if (colorScheme) Color.Black else Color.White,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            }
            else {
                DropdownMenuItem(onClick = {
                    scope.launch {
                        storeFavLines.saveFavoriteLine(rowLine.id.toString())
                        linesByGroup.value.clear()
                        linesByGroup.value.addAll(Lines.getAllLinesBySection(context))
                    }
                    menuShown.value = false
                }) {
                    Row {
                        Icon(
                            imageVector = Icons.Rounded.FavoriteBorder,
                            contentDescription = null,
                            tint = if (colorScheme) Color.Black else Color.White,
                            modifier = Modifier
                                .size(30.dp)
                        )

                        Spacer(modifier = Modifier
                            .width(5.dp)
                        )

                        Text(
                            text = "Ajouter aux favoris",
                            color = if (colorScheme) Color.Black else Color.White,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }
}