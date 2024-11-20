package model.DTO

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
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
}