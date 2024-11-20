package model.DTO

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

class Service (val id: Int,
               val vehicleId: Int,
               val lineId: Int,
               val currentSpeed: Int,
               val state: String,
               val stateTime: Int,
               val destination: String,
               val latitude: Double,
               val longitude: Double,
               val currentStop: Int,
               val path: Int,
               val timestamp: Date,
               val vehicle: Vehicle = Vehicles.getVehicle(vehicleId.toString())
) : Parcelable {
    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        vehicleId = parcel.readInt(),
        lineId = parcel.readInt(),
        currentSpeed = parcel.readInt(),
        state = parcel.readString() ?: "",
        stateTime = parcel.readInt(),
        destination = parcel.readString() ?: "",
        latitude = parcel.readDouble(),
        longitude = parcel.readDouble(),
        currentStop = parcel.readInt(),
        path = parcel.readInt(),
        timestamp = Date(parcel.readLong()),
        vehicle = parcel.readParcelable(Vehicle::class.java.classLoader) ?: throw IllegalStateException("Vehicle cannot be null")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(vehicleId)
        parcel.writeInt(lineId)
        parcel.writeInt(currentSpeed)
        parcel.writeString(state)
        parcel.writeInt(stateTime)
        parcel.writeString(destination)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeInt(currentStop)
        parcel.writeInt(path)
        parcel.writeLong(timestamp.time)
        parcel.writeParcelable(vehicle, flags)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Service> {
        override fun createFromParcel(parcel: Parcel): Service = Service(parcel)
        override fun newArray(size: Int): Array<Service?> = arrayOfNulls(size)
    }
}