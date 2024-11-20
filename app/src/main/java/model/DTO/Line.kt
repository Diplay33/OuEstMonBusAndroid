package model.DTO

import android.os.Parcel
import android.os.Parcelable
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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        network = parcel.readString() ?: "",
        id = parcel.readInt(),
        name = parcel.readString() ?: "",
        type = parcel.readString() ?: "",
        index = parcel.readInt(),
        section = parcel.readInt(),
        physicalType = parcel.readString() ?: "",
        imageUrl = parcel.readString() ?: "",
        colorHex = parcel.readString() ?: "",
        isNest = parcel.readByte() != 0.toByte(),
        showSchedules = parcel.readByte() != 0.toByte(),
        parentId = parcel.readInt(),
        createdAt = parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(network)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeInt(index ?: 0)
        parcel.writeInt(section ?: 0)
        parcel.writeString(physicalType)
        parcel.writeString(imageUrl)
        parcel.writeString(colorHex)
        parcel.writeByte(if (isNest) 1 else 0)
        parcel.writeByte(if (showSchedules) 1 else 0)
        parcel.writeInt(parentId ?: 0)
        parcel.writeString(createdAt)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Line> {
        override fun createFromParcel(parcel: Parcel): Line = Line(parcel)
        override fun newArray(size: Int): Array<Line?> = arrayOfNulls(size)
    }
}