package view.next_schedules.search_line

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import model.DTO.*
import model.preferences_data_store.StoreChosenNetwork
import model.preferences_data_store.StoreFavoriteLines
import view.lines_map_list.ColorIndicatorDot

@Composable
fun SearchLineViewRow(
    linesByGroup: MutableState<MutableList<List<Line>>>,
    line: Line,
    navController: NavController,
    isLineInService: Boolean?
) {
    val isCollapsed = rememberSaveable {
        mutableStateOf(true)
    }
    val currentRotation = rememberSaveable {
        mutableStateOf(0f)
    }
    val rotation = remember {
        Animatable(currentRotation.value)
    }
    val scope = rememberCoroutineScope()
    val paths = rememberSaveable {
        mutableStateOf<MutableList<List<Path>>>(mutableListOf())
    }
    val isLoading = remember {
        mutableStateOf(false)
    }
    val menuShown = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val storeFavLines = StoreFavoriteLines(context, line.id.toString())
    val isFavorite = storeFavLines.isFavorite.collectAsState(initial = false)
    val colorScheme = !isSystemInDarkTheme()
    val storeChosenNetwork = StoreChosenNetwork(context)
    val network = rememberSaveable {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        storeChosenNetwork.chosenNetwork.collect { network.value = it ?: "" }
        if(!isCollapsed.value && currentRotation.value == 0f) {
            scope.launch {
                rotation.animateTo(
                    targetValue = 90f,
                    animationSpec = tween(0)
                )
            }
        }
    }

    Column(modifier = Modifier
        .padding(horizontal = 15.dp)
        .padding(vertical = 5.dp)
        .background(
            if (colorScheme) Color.Transparent else Color(0xff18191A),
            shape = RoundedCornerShape(10.dp)
        )
        .background(
            Color(android.graphics.Color.parseColor(line.colorHex)).copy(alpha = 0.2f),
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
                        if (isCollapsed.value && paths.value.isEmpty()) {
                            isLoading.value = true
                            Paths.getOrderedPathsByLine(network.value, line.id) { returnedPaths ->
                                paths.value = returnedPaths.toMutableList()
                                isLoading.value = false
                                isCollapsed.value = !isCollapsed.value
                                scope.launch {
                                    rotation.animateTo(
                                        targetValue = if (isCollapsed.value) 0f else 90f,
                                        animationSpec = tween(200, easing = LinearEasing)
                                    )
                                }
                            }
                        } else {
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
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(line.imageUrl)
                            .decoderFactory(SvgDecoder.Factory())
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                    )

                    Spacer(modifier = Modifier
                        .width(15.dp)
                    )

                    Text(
                        text = line.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = if (colorScheme) Color.Black else Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                }

                Row(modifier = Modifier
                    .align(Alignment.CenterVertically)
                ) {
                    if(isLineInService != null) {
                        ColorIndicatorDot(
                            color = if (isLineInService == true)
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
                                tint = if (colorScheme) Color.Black else Color.White,
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
                    Color(android.graphics.Color.parseColor(line.colorHex)).copy(alpha = 0.2f),
                    shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                )
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .padding(top = 15.dp)
            ) {
                paths.value.forEach { paths ->
                    if(paths.isNotEmpty()) {
                        SearchLineViewDestination(paths, line, navController)
                    }
                }
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
                        storeFavLines.removeFromFavorites(line.id.toString())
                        linesByGroup.value = Lines.getAllLinesBySection(context, true).toMutableList()
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
                        storeFavLines.saveFavoriteLine(line.id.toString())
                        linesByGroup.value = Lines.getAllLinesBySection(context, true).toMutableList()
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