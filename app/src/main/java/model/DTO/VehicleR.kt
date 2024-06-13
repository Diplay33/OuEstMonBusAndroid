package model.DTO

import androidx.room.Entity

@Entity(primaryKeys = ["id", "network"])
data class VehicleR (
    var id: String,
    var network: String,
    var parkId: String,
    var brand: String,
    var model: String,
    var type: String,
    var operator: String,
    var tciId: Int
)