package model.DAO

import model.DAO.access_data.networks
import model.DTO.Network

class NetworkDAO {
    companion object {
        fun getAllNetworks(): List<Network> {
            return networks.sortedBy { it.shortName }
        }

        fun getNetwork(shortName: String): Network? {
            return networks.firstOrNull { it.shortName == shortName }
        }
    }
}