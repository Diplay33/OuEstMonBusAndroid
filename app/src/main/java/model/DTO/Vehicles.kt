package model.DTO

import model.DAO.VehicleDAO

class Vehicles {
    companion object {
        fun getVehicleById(id: String): Vehicle {
            return VehicleDAO.getVehicleById(id)
        }
    }
}