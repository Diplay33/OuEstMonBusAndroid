package view.lines_map_list.line_map

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import model.DTO.Line

@Composable
fun LineMapViewTopBar(
    navController: NavController,
    line: Line,
    isUserLocationShown: MutableState<Boolean>,
    cameraPositionState: CameraPositionState,
    userPosition: MutableState<LatLng>
) {
    val context = LocalContext.current
    val isDialogShown = remember {
        mutableStateOf(false)
    }
    val isErrorDialogShown = remember {
        mutableStateOf(false)
    }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(!isGranted) {
            isDialogShown.value = true
        }
    }

    if(isDialogShown.value) {
        AlertDialog(
            onDismissRequest = {
                isDialogShown.value = false
            },
            title = {
                Text("Services de localisation désactivés")
            },
            text = {
                Text("Pour pouvoir vous localiser sur la carte, veuillez autoriser " +
                        "l'application à accéder aux services de localisation depuis vos " +
                        "paramètres système")
            },
            confirmButton = {
                Button(
                    onClick = {
                        isDialogShown.value = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

    if(isErrorDialogShown.value) {
        AlertDialog(
            onDismissRequest = {
                isErrorDialogShown.value = false
            },
            title = {
                Text("Erreur")
            },
            text = {
                Text("Une erreur s'est produite lors de la récupération de votre position, " +
                        "veuillez réessayer")
            },
            confirmButton = {
                Button(
                    onClick = {
                        isErrorDialogShown.value = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

    TopAppBar(backgroundColor = Color.Transparent, elevation = 0.dp) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .clickable { navController.navigateUp() }
                    .size(30.dp)
                    .align(Alignment.CenterVertically)
            )

            Text(
                text = line.lineName,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .background(
                        color = colorResource(id = line.lineColorResource),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(7.dp)
            )

            Icon(
                imageVector = Icons.Rounded.LocationOn,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        //Get location permission
                        if (ContextCompat.checkSelfPermission(
                                context,
                                ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            val fusedLocationProviderClient =
                                LocationServices.getFusedLocationProviderClient(context)

                            try {
                                val locationResult = fusedLocationProviderClient.lastLocation
                                locationResult.addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val lastKnownLocation = task.result

                                        if (lastKnownLocation != null) {
                                            cameraPositionState.position =
                                                CameraPosition.fromLatLngZoom(
                                                    LatLng(
                                                        lastKnownLocation.latitude - 0.013,
                                                        lastKnownLocation.longitude
                                                    ), 13f
                                                )
                                            userPosition.value = LatLng(
                                                lastKnownLocation.latitude,
                                                lastKnownLocation.longitude
                                            )
                                            isUserLocationShown.value = true
                                        } else {
                                            isErrorDialogShown.value = true
                                            Log.d(
                                                "USER_LOCATION_ERROR",
                                                "Error during getting user position process"
                                            )
                                        }
                                    } else {
                                        isErrorDialogShown.value = true
                                        Log.d(
                                            "USER_LOCATION_ERROR",
                                            "Error during getting user position process"
                                        )
                                    }
                                }
                            }
                            catch(e: SecurityException) {
                                print("Error: $e")
                            }
                        } else {
                            launcher.launch(ACCESS_FINE_LOCATION)
                        }
                    }
                    .size(30.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}