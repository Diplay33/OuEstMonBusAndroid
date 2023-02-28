package model.DAO.AccessData

class PathDestinationData {
    companion object {
        val destinations: Map<String, List<String>> = mapOf(
            //A
            "Aéroport" to listOf("MERIGNAC", "Aéroport", ""),
            "Alfred De Vigny" to listOf("MERIGNAC", "Alfred de Vigny", ""),
            //B
            "Barriere Du Medoc" to listOf("BORDEAUX", "Barrière du Médoc", ""),
            "Berges De La Garonne" to listOf("BORDEAUX", "Berges de la Garonne", ""),
            "Bougnard" to listOf("PESSAC", "Bougnard", ""),
            "Buttiniere" to listOf("LORMONT", "Buttinière", ""),
            //C
            "Cantinolle" to listOf("EYSINES", "Cantinolle", ""),
            "Capc Musee D'Art Contemporain" to listOf("BORDEAUX", "CAPC Musée d'Art Contemporain", ""),
            "Cap Metiers" to listOf("PESSAC", "Cap Métiers", ""),
            "Carbon-Blanc" to listOf("BASSENS/CARBON BLANC", "La Gardette", ""),
            "Carle Vernet" to listOf("BORDEAUX", "Carle Vernet", ""),
            "Cenon Gare" to listOf("CENON", "Gare", ""),
            "Chataigneraie" to listOf("PESSAC", "Châtaigneraie", ""),
            "Claveau" to listOf("BORDEAUX", "Claveau", ""),
            "Cracovie" to listOf("BORDEAUX", "Cracovie", ""),
            //D
            "Doyen Brus" to listOf("PESSAC", "Doyen Brus", ""),
            //E
            "Eysines Centre" to listOf("EYSINES", "Centre", ""),
            //F
            "Floirac Dravemont" to listOf("FLOIRAC", "Dravemont", ""),
            "Fontaine D'Arlac" to listOf("MERIGNAC", "Fontaine d'Arlac", ""),
            "France Alouette" to listOf("PESSAC", "France Alouette", ""),
            //G
            "Galin" to listOf("BORDEAUX", "Galin", ""),
            "Gare De Begles" to listOf("BÈGLES", "Gare", ""),
            "Gare De Blanquefort" to listOf("BLANQUEFORT", "Gare", ""),
            "Gare De Pessac Alouette" to listOf("PESSAC", "Gare Pessac Alouette", ""),
            "Gare Saint-Jean" to listOf("BORDEAUX", "Gare Saint Jean", ""),
            "Gare St Jean" to listOf("BORDEAUX", "Gare Saint Jean", ""),
            "Gavinies" to listOf("BORDEAUX", "Gaviniès", ""),
            "Grand Parc" to listOf("BORDEAUX", "Grand Parc", ""),
            //H
            "Hippodrome" to listOf("LE BOUSCAT", "Hippodrome", ""),
            "Hopital Haut Leveque" to listOf("PESSAC", "Hôpital Haut Levêque", ""),
            "Hopital Pellegrin" to listOf("BORDEAUX", "Hôpital Pellegrin", ""),
            //I
            "Issac" to listOf("SAINT MÉDARD EN JALLES", "Issac", ""),
            //L
            "La Cite Du Vin" to listOf("BORDEAUX", "La Cité du Vin", ""),
            "La Morlette" to listOf("CENON", "La Morlette", ""),
            "Lauriers" to listOf("LORMONT", "Lauriers", ""),
            "La Vache" to listOf("BRUGES", "La Vache", ""),
            "Le Haillan Rostand" to listOf("LE HAILLAN", "Rostand", ""),
            "Les Aubiers" to listOf("BORDEAUX", "Les Aubiers", ""),
            "Les Sources" to listOf("EYSINES", "Les Sources", ""),
            "Lycee Vaclav Havel" to listOf("BÈGLES", "Lycée Vàclav Havel", ""),
            //M
            "Mairie Du Bouscat" to listOf("LE BOUSCAT", "Mairie", ""),
            "Meriadeck" to listOf("BORDEAUX", "Meriadeck", ""),
            "Merignac Centre" to listOf("MERIGNAC", "Centre", ""),
            "Montaigne-Montesquieu" to listOf("PESSAC", "Montaigne Montesquieu", ""),
            "Musee D'Aquitaine" to listOf("BORDEAUX", "Musée d'Aquitaine", ""),
            "Museum" to listOf("BORDEAUX", "Fondaudège Muséum", ""),
            //N
            "New York" to listOf("BORDEAUX", "New York", ""),
            "Nouveau Stade" to listOf("BORDEAUX", "Parc des Expositions", ""),
            //P
            "Palais de Justice" to listOf("BORDEAUX", "Palais de Justice", ""),
            "Peixotto" to listOf("TALENCE", "Peixotto", ""),
            "Pessac Centre" to listOf("PESSAC", "Centre", ""),
            "Picot" to listOf("EYSINES", "Picot", ""),
            "Pin Galant" to listOf("MERIGNAC", "Pin Galant", ""),
            "Place De La Bourse" to listOf("BORDEAUX", "Place de la Bourse", ""),
            "Porte De Bourgogne" to listOf("BORDEAUX", "Porte de Bourgogne", ""),
            //Q
            "Quarante Journaux" to listOf("BORDEAUX", "Quarante Journaux", ""),
            "Quatre Chemins" to listOf("MERIGNAC", "Quatre Chemins", ""),
            "Quinconces" to listOf("BORDEAUX", "Quinconces", ""),
            "Quinconces Fleuve" to listOf("BORDEAUX", "Quinconces Fleuve", ""),
            //R
            "Rue Achard" to listOf("BORDEAUX", "Rue Achard", ""),
            //S
            "Saint-Augustin" to listOf("BORDEAUX", "Saint Augustin", ""),
            "Sainte-Catherine" to listOf("BORDEAUX", "Sainte Catherine", ""),
            "Saint-Michel" to listOf("BORDEAUX", "Saint Michel", ""),
            "Saint-Nicolas" to listOf("BORDEAUX", "Saint Nicolas", ""),
            "Stalingrad" to listOf("BORDEAUX", "Stalingrad", ""),
            //T
            "Terres Neuves" to listOf("BÈGLES", "Terres Neuves", ""),
            "Term Aeroport" to listOf("MERIGNAC", "Aéroport", ""),
            "Thiers-Benauge" to listOf("BORDEAUX", "Thiers Benauge", ""),
            "Treulon" to listOf("LE BOUSCAT", "Champs de Courses - Treulon", ""),
            //V
            "Villenave Pyrénées" to listOf("VILLENAVE D'ORNON", "Pyrénées", ""),
            "Villepreux via LSMED" to listOf("SAINT AUBIN", "Villepreux", "via Lycée Sud Médoc"),
        )
    }
}