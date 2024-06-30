package model.DTO

import model.DAO.NetworkDAO

class Networks {
    companion object {
        fun getAllNetworks(): List<Network> {
            return NetworkDAO.getAllNetworks()
        }

        fun getNetwork(shortName: String): Network? {
            return NetworkDAO.getNetwork(shortName)
        }
    }
}