package model.DTO

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Station(val id: String,
              val stationId: String,
              val name: String,
              val latitude: Double,
              val longitude : Double
) : Parcelable