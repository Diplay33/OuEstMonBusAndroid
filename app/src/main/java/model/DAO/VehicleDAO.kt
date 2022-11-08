package model.DAO

import model.DAO.AccessData.VehicleData
import model.DTO.Vehicle

class VehicleDAO {
    companion object {
        fun getVehicleById(id: String): Vehicle {
            val vehicleDict = VehicleData.vehicles.filter { it["id"] == id }
            return try {
                val foundVehicle = vehicleDict.first()
                Vehicle(
                    id = foundVehicle["id"]!!.toInt(),
                    parkId = foundVehicle["parkId"]!!.toInt(),
                    brand = foundVehicle["brand"]!!,
                    model = foundVehicle["model"]!!,
                    type = foundVehicle["type"]!!,
                    operator = foundVehicle["operator"]!!
                )
            }
            catch(e: Exception) {
                getUnknownVehicle(id.toInt())
            }
        }

        private fun getUnknownVehicle(id: Int): Vehicle {
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