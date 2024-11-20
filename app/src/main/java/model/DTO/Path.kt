package model.DTO

import android.os.Parcel
import android.os.Parcelable

class Path(
    val id: Int,
    val name: String,
    val direction: String,
    val coordinates: List<List<List<Double>>>
) : Parcelable {
    fun getDestinationName(): String {
        var destinationName = ""
        var writeInName = false

        name.forEach { char ->
            if(writeInName) {
                destinationName += char
            }

            if(char == "-".first()) {
                writeInName = true
            }
        }

        return destinationName.trim()
    }

    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        name = parcel.readString() ?: "",
        direction = parcel.readString() ?: "",
        coordinates = mutableListOf<List<List<Double>>>().apply {
            parcel.readList(this as List<*>, List::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(direction)
        parcel.writeList(coordinates)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Path> {
        override fun createFromParcel(parcel: Parcel): Path = Path(parcel)
        override fun newArray(size: Int): Array<Path?> = arrayOfNulls(size)
    }
}
