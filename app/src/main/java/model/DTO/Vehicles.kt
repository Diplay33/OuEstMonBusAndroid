package model.DTO

import com.diplay.ouestmonbus.MainApplication

class Vehicles {
    companion object {
        private val vehicleDAO = MainApplication.appDatabase.getVehicleDAO()

        //MARK: - GET

        fun getVehicle(id: String): Vehicle {
            return vehicleDAO.getVehicle(id) ?: getUnknowVehicle(id)
        }
        
        fun getUnknowVehicle(id: String): Vehicle {
            return Vehicle(
                id = id,
                network = "",
                parkId = id,
                brand = "",
                model = "Modèle de véhicule inconnu",
                type = "UNKNOWN",
                operator = "Inconnu",
                tciId = null
            )
        }

        //MARK: - SET

        fun insertVehicles(vehicles: List<Vehicle>) {
            vehicleDAO.insertVehicles(vehicles)
        }

        fun deleteContent() {
            vehicleDAO.deleteContent()
        }
    }
}