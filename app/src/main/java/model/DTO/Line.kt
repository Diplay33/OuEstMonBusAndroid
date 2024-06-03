package model.DTO

import androidx.room.Entity

@Entity(primaryKeys = ["network", "id"])
data class Line (
    var network: String,
    var id: Int,
    var name: String,
    var type: String,
    var index: Int?,
    var section: Int?,
    var physicalType: String,
    var imageUrl: String?,
    var colorHex: String?,
    var isNest: Boolean,
    var showSchedules: Boolean,
    var parentId: Int?,
    var createdAt: String
)