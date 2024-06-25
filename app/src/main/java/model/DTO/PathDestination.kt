package model.DTO

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class PathDestination (
    @PrimaryKey
    var id: Int,
    var input: String,
    var city: String,
    var destination: String,
    var by: String?,
    @SerialName("related_line") var relatedLine: Int?,
    var network: String
)