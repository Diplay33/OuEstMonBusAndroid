package model.DTO

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Destination(
    @PrimaryKey
    var id: Int,
    var input: String,
    var city: String,
    var destination: String,
    var relatedLine: Int?,
    var network: String
)