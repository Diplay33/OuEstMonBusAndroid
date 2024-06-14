package model.DTO

import androidx.room.Entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(primaryKeys = ["network", "id"])
data class Line (
    var network: String,
    var id: Int,
    var name: String,
    var type: String,
    var index: Int?,
    var section: Int?,
    @SerialName("physical_type") var physicalType: String,
    @SerialName("image_url") var imageUrl: String?,
    @SerialName("color_hex") var colorHex: String?,
    @SerialName("is_nest") var isNest: Boolean,
    @SerialName("show_schedules") var showSchedules: Boolean,
    @SerialName("parent_id") var parentId: Int?,
    @SerialName("created_at") var createdAt: String
)