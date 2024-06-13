package model.DTO

import com.diplay.ouestmonbus.MainApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VehiclesR {
    companion object {
        private val vehicleDAO = MainApplication.appDatabase.getVehicleDAO()

        fun getVehicle(id: String): VehicleR {
            return vehicleDAO.getVehicle(id) ?: getUnknowVehicle(id)
        }
        
        fun getUnknowVehicle(id: String): VehicleR {
            return VehicleR(
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
    }
}