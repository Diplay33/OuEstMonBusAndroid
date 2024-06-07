package model.DTO

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RetourDestination (
    @PrimaryKey
    var id: Int,
    var input: Int,
    var city1: String,
    var destination1: String,
    var city2: String?,
    var destination2: String?,
    var city3: String?,
    var destination3: String?,
    var network: String
)