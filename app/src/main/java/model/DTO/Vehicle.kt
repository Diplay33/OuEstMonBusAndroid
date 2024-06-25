package model.DTO

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
) {
    @Ignore val fullName: String = "$brand $model".trim()
}