package model.DTO

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(primaryKeys = ["id", "network"])
data class Vehicle (
    var id: String,
    var network: String,
    @SerialName("park_id") var parkId: String,
    var brand: String,
    var model: String,
    var type: String,
    var operator: String,
    @SerialName("tci_id") var tciId: Int?
) : Parcelable {
    @Ignore val fullName: String = "$brand $model".trim()

    constructor(parcel: Parcel) : this(
        id = parcel.readString() ?: "",
        network = parcel.readString() ?: "",
        parkId = parcel.readString() ?: "",
        brand = parcel.readString() ?: "",
        model = parcel.readString() ?: "",
        type = parcel.readString() ?: "",
        operator = parcel.readString() ?: "",
        tciId = parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(network)
        parcel.writeString(parkId)
        parcel.writeString(brand)
        parcel.writeString(model)
        parcel.writeString(type)
        parcel.writeString(operator)
        parcel.writeValue(tciId)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Vehicle> {
        override fun createFromParcel(parcel: Parcel): Vehicle = Vehicle(parcel)
        override fun newArray(size: Int): Array<Vehicle?> = arrayOfNulls(size)
    }
}