package model.DAO

import model.DAO.AccessData.VehicleData
import model.DTO.Vehicle

class VehicleDAO {
    companion object {
        fun getVehicleById(id: String): Vehicle {
            val vehicleDict = VehicleData.vehicles.filter { it["id"] == id }
            try {
                val foundVehicle = vehicleDict.first()
                return Vehicle(
                    id = foundVehicle["id"]!!.toInt(),
                    parkId = foundVehicle["parkId"]!!.toInt(),
                    brand = foundVehicle["brand"]!!,
                    model = foundVehicle["model"]!!,
                    type = foundVehicle["type"]!!,
                    operator = foundVehicle["operator"]!!
                )
            }
            catch(e: Exception) {
                return getUnknownVehicle(id.toInt())
            }
        }

        fun getUnknownVehicle(id: Int): Vehicle {
            return Vehicle(
                id = id,
                parkId = id,
                brand = "INCONNU",
                model = "Modèle de véhicule inconnu",
                type = "INCONNU",
                operator = "Inconnu"
            )
        }
    }
}