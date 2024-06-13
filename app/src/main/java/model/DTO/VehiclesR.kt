package model.DTO

import com.diplay.ouestmonbus.MainApplication

class VehiclesR {
    companion object {
        private val vehicleDAO = MainApplication.appDatabase.getVehicleDAO()
    }
}