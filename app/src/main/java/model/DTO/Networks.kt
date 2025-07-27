package model.DTO

import model.DAO.NetworkDAO
import java.text.Normalizer

class Networks {
    companion object {
        fun getAllNetworks(): List<Network> {
            return NetworkDAO.getAllNetworks()
        }

        fun getNetworksBySearchString(searchString: String): List<Network> {
            val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()
            fun CharSequence.unaccent(): String {
                val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
                return REGEX_UNACCENT.replace(temp, "")
            }

            return NetworkDAO.getAllNetworks().filter { network ->
                val processedSearchString = searchString.unaccent().lowercase().trim()
                network.shortName.lowercase().trim().unaccent().contains(processedSearchString) || network.fullName.lowercase().trim().unaccent().contains(searchString)
            }
        }

        fun getNetwork(shortName: String): Network? {
            return NetworkDAO.getNetwork(shortName)
        }
    }
}