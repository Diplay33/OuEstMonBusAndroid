package model.DTO

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Station(val id: Int,
              val stationId: String,
              val name: String,
              val latitude: Double,
              val longitude : Double
) : Parcelable