package model.DTO

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
)