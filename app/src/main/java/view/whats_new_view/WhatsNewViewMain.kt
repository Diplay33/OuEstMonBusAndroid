package view.whats_new_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.PhonelinkRing
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Stars
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.diplay.ouestmonbus.BuildConfig
import com.diplay.ouestmonbus.R
import model.DTO.WhatsNewElement
import view.Screens.SplashScreens

@Composable
fun WhatsNewViewMain(navController: NavController, showSplashScreen: MutableState<Boolean>, network: String?) {
    val colorScheme = !isSystemInDarkTheme()
    val whatsNewElements = listOf(
        WhatsNewElement(
            icon = Icons.Rounded.Search,
            title = "Recherche par modèle",
            bodyText = "Dans la liste de tous les véhicules en circulation, vous pouvez rechercher par modèle de véhicule."
        ),
        WhatsNewElement(
            icon = Icons.Rounded.Stars,
            title = "Ajout d'un nouveau réseau !",
            bodyText = "Les habitants de la métropole de Montpellier peuvent désormais localiser leurs trams et bus !"
        ),
        WhatsNewElement(
            icon = Icons.Rounded.PhonelinkRing,
            title = "Navigation améliorée",
            bodyText = "La navigation au sein de l'application a été améliorée, un retour en arrière ne recharge plus forcément la page."
        )
    )

    Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxHeight()
        .background(if (colorScheme) Color.White else Color.Black)
    ) {
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .weight(0.1f)
        ) {
            Spacer(modifier = Modifier
                .height(50.dp)
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.app_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .shadow(
                            elevation = 10.dp,
                            spotColor = Color.Black.copy(0.5f),
                            ambientColor = Color.Black.copy(0.1f),
                            shape = RoundedCornerShape(35.dp)
                        )
                        .clip(RoundedCornerShape(35.dp))
                        .size(150.dp)
                )

                Text(
                    text = "Quoi de neuf dans la version ${BuildConfig.VERSION_NAME} ?",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = if (colorScheme) Color.Black else Color.White,
                    modifier = Modifier
                        .padding(top = 15.dp)
                )

            }

            Spacer(modifier = Modifier
                .height(50.dp)
            )

            whatsNewElements.forEach { element ->
                WhatsNewViewRow(element, modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .padding(bottom = 15.dp)
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = 50.dp)
                .fillMaxWidth()
                .height(45.dp)
                .padding(horizontal = 20.dp)
                .shadow(
                    elevation = 10.dp,
                    spotColor = Color.Black.copy(0.7f),
                    ambientColor = Color.Black.copy(0.3f),
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(RoundedCornerShape(10.dp))
                .background(Color(android.graphics.Color.parseColor("#007AFF")))
                .clickable {
                    if (network != null) {
                        showSplashScreen.value = false
                    } else {
                        navController.navigate(SplashScreens.NetworkPicker.route)
                    }
                }
        ) {
            Text(
                text = "Continuer",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}