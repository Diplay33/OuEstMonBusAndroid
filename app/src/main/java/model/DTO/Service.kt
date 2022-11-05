package model.DTO

class Service (val id: Int,
               val vehicleId: Int,
               val line: Int,
               val currentSpeed: Int,
               val state: String,
               val stateTime: Int,
               val destination: String,
               val latitude: Double,
               val longitude: Double,
               val currentStop: Int,
               var vehicle: Vehicle = Vehicle(1, 1, "", "", "", "")
)