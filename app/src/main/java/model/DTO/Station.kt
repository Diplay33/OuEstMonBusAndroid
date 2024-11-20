package model.DTO

import android.os.Parcel
import android.os.Parcelable

class Station(val id: Int,
              val stationId: String,
              val name: String,
              val latitude: Double,
              val longitude : Double
) : Parcelable {

    // Constructor pour recréer l'objet à partir d'un Parcel
    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        stationId = parcel.readString() ?: "",
        name = parcel.readString() ?: "",
        latitude = parcel.readDouble(),
        longitude = parcel.readDouble()
    )

    // Méthode pour écrire les données dans le Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(stationId)
        parcel.writeString(name)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Station> {
        override fun createFromParcel(parcel: Parcel): Station = Station(parcel)
        override fun newArray(size: Int): Array<Station?> = arrayOfNulls(size)
    }
}