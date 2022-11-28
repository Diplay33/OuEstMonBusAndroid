package view.next_schedules.search_line

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import model.DAO.AccessData.DestinationAllerData
import model.DTO.*
import model.preferences_data_store.StoreFavoriteLines
import view.lines_map_list.ColorIndicatorDot

@Composable
fun SearchLineViewRow(
    linesByGroup: SnapshotStateList<List<Line>>,
    line: Line,
    navController: NavController
) {
    val isCollapsed = remember {
        mutableStateOf(true)
    }
    val currentRotation = remember {
        mutableStateOf(0f)
    }
    val rotation = remember {
        Animatable(currentRotation.value)
    }
    val scope = rememberCoroutineScope()
    val paths = remember {
        mutableStateListOf<List<Path>>()
    }
    val isLoading = remember {
        mutableStateOf(false)
    }
    val isLineInService = remember {
        mutableStateOf<Boolean?>(null)
    }
    val menuShown = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val storeFavLines = StoreFavoriteLines(context, line.id.toString())
    val isFavorite = storeFavLines.isFavorite.collectAsState(initial = false)

    LaunchedEffect(line) {
        isLineInService.value = null
        Services.getServicesByLine(line.id) { services ->
            isLineInService.value = services.isNotEmpty()
        }
    }

    Column(modifier = Modifier
        .padding(horizontal = 15.dp)
        .padding(vertical = 5.dp)
        .background(
            colorResource(id = line.lineColorResource).copy(alpha = 0.2f),
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        Row(modifier = Modifier
            .height(45.dp)
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { },
                    onDoubleTap = { },
                    onLongPress = { menuShown.value = true },
                    onTap = {
                        if(isCollapsed.value && paths.isEmpty()) {
                            isLoading.value = true
                            Paths.getOrderedPathsByLine(line.id) { returnedPaths ->
                                paths.clear()
                                paths.addAll(returnedPaths)
                                isLoading.value = false
                                isCollapsed.value = !isCollapsed.value
                                scope.launch {
                                    rotation.animateTo(
                                        targetValue = if (isCollapsed.value) 0f else 90f,
                                        animationSpec = tween(200, easing = LinearEasing)
                                    )
                                }
                            }
                        }
                        else {
                            isCollapsed.value = !isCollapsed.value
                            scope.launch {
                                rotation.animateTo(
                                    targetValue = if (isCollapsed.value) 0f else 90f,
                                    animationSpec = tween(200, easing = LinearEasing)
                                )
                            }
                        }
                    }
                )
            }
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
            ) {
                Row {
                    Image(
                        painter = painterResource(id = line.lineImageResource),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                    )

                    Spacer(modifier = Modifier
                        .width(15.dp)
                    )

                    Text(
                        text = line.lineName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                }

                Row(modifier = Modifier
                    .align(Alignment.CenterVertically)
                ) {
                    if(isLineInService.value != null) {
                        ColorIndicatorDot(
                            color = if (isLineInService.value == true)
                                Color.Green
                            else
                                Color.Red,
                            size = 15.dp,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                    }

                    Spacer(modifier = Modifier
                        .width(15.dp)
                    )

                    Box(contentAlignment = Alignment.Center, modifier = Modifier
                        .align(Alignment.CenterVertically)
                    ) {
                        Box(modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color.Gray.copy(alpha = 0.2f))
                            .align(Alignment.Center)
                        )

                        if(isLoading.value) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        }
                        else {
                            Icon(
                                imageVector = Icons.Rounded.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier
                                    .rotate(rotation.value)
                            )
                        }
                    }
                }
            }
        }

        if(!isCollapsed.value) {
            Column(modifier = Modifier
                .background(
                    colorResource(id = line.lineColorResource).copy(alpha = 0.2f),
                    shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                )
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .padding(top = 15.dp)
            ) {
                paths.forEach { paths ->
                    if(paths.isNotEmpty()) {
                        SearchLineViewDestinationRow(
                            paths = paths,
                            destinations = if (paths.first().direction == "ALLER")
                                DestinationsAller.getDestinationAllerOfLine(line.id)
                            else
                                DestinationsRetour.getDestinationRetourOfLine(line.id),
                            navController = navController
                        )

                        Spacer(modifier = Modifier
                            .height(15.dp)
                        )
                    }
                }
            }
        }

        DropdownMenu(expanded = menuShown.value, onDismissRequest = { menuShown.value = false }) {
            if(isFavorite.value == true) {
                DropdownMenuItem(onClick = {
                    scope.launch {
                        storeFavLines.removeFromFavorites(line.id.toString())
                        linesByGroup.clear()
                        linesByGroup.addAll(Lines.getLinesByGroup(context))
                    }
                    menuShown.value = false
                }) {
                    Row {
                        Icon(
                            imageVector = Icons.Rounded.Favorite,
                            contentDescription = null,
                            modifier = Modifier
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
                        storeFavLines.saveFavoriteLine(line.id.toString())
                        linesByGroup.clear()
                        linesByGroup.addAll(Lines.getLinesByGroup(context))
                    }
                    menuShown.value = false
                }) {
                    Row {
                        Icon(
                            imageVector = Icons.Rounded.FavoriteBorder,
                            contentDescription = null,
                            modifier = Modifier
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