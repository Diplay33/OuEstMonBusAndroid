package model.DTO

import androidx.room.Entity
import androidx.room.Ignore

@Entity(primaryKeys = ["id", "network"])
data class Vehicle (
    var id: String,
    var network: String,
    var parkId: String,
    var brand: String,
    var model: String,
    var type: String,
    var operator: String,
    var tciId: Int?
) {
    @Ignore val fullName: String = "$brand $model".trim()
}