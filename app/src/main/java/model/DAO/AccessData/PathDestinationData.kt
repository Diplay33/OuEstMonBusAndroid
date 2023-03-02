package model.DAO.AccessData

class PathDestinationData {
    companion object {
        val destinations: Map<String, List<String>> = mapOf(
            //5
            "5 Chemins" to listOf("LE HAILLAN", "5 Chemins", ""),
            "5 Chemins via THALES" to listOf("LE HAILLAN", "5 Chemins", "via Thalès"),
            //A
            "Aéroport" to listOf("MERIGNAC", "Aéroport", ""),
            "Alfred De Vigny" to listOf("MERIGNAC", "Alfred de Vigny", ""),
            //B
            "Barrière d'Ornano" to listOf("BORDEAUX", "Barrière d'Ornano", ""),
            "Barriere Du Medoc" to listOf("BORDEAUX", "Barrière du Médoc", ""),
            "Base sous-marine" to listOf("BORDEAUX", "Base sous-marine", ""),
            "Bassins à Flot" to listOf("BORDEAUX", "Bassins à Flot", ""),
            "Beaudésert" to listOf("MERIGNAC", "Beaudésert", ""),
            "Beausoleil" to listOf("GRADIGNAN", "Beausoleil", ""),
            "Berges De La Garonne" to listOf("BORDEAUX", "Berges de la Garonne", ""),
            "Bougnard" to listOf("PESSAC", "Bougnard", ""),
            "Brandenburg" to listOf("BORDEAUX", "Brandenburg", ""),
            "Buttiniere" to listOf("LORMONT", "Buttinière", ""),
            //C
            "Cantinolle" to listOf("EYSINES", "Cantinolle", ""),
            "Capc Musee D'Art Contemporain" to listOf("BORDEAUX", "CAPC Musée d'Art Contemporain", ""),
            "Cap de Bos" to listOf("PESSAC", "Cap de Bos", ""),
            "Cap Metiers" to listOf("PESSAC", "Cap Métiers", ""),
            "Carbon-Blanc" to listOf("BASSENS/CARBON BLANC", "La Gardette", ""),
            "Carbon Blanc)" to listOf("BASSENS/CARBON BLANC", "La Gardette", ""),
            "Carle Vernet" to listOf("BORDEAUX", "Carle Vernet", ""),
            "Cenon Gare" to listOf("CENON", "Gare", ""),
            "Centre Commercial du Lac" to listOf("BORDEAUX", "Centre Commercial du Lac", ""),
            "Centre Commercial du Lac via PA" to listOf("BORDEAUX", "Centre Commercial du Lac", "via Pont d'Aquitaine"),
            "Chataigneraie" to listOf("PESSAC", "Châtaigneraie", ""),
            "Claveau" to listOf("BORDEAUX", "Claveau", ""),
            "Courréjean" to listOf("VILLENAVE D'ORNON", "Courréjean", ""),
            "Courréjean via IMA" to listOf("VILLENAVE D'ORNON", "Courréjean", "via IMA"),
            "Courréjean via THALES" to listOf("VILLENAVE D'ORNON", "Courréjean", "via Thalès"),
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
            "Gare Routière" to listOf("SAINT MÉDARD EN JALLES", "Gare Routière", ""),
            "Gare Saint-Jean" to listOf("BORDEAUX", "Gare Saint Jean", ""),
            "Gare St Jean" to listOf("BORDEAUX", "Gare Saint Jean", ""),
            "Gavinies" to listOf("BORDEAUX", "Gaviniès", ""),
            "Gaviniès" to listOf("BORDEAUX", "Gaviniès", ""),
            "Gradignan Centre" to listOf("GRADIGNAN", "Centre", ""),
            "Grand Parc" to listOf("BORDEAUX", "Grand Parc", ""),
            //H
            "Hippodrome" to listOf("LE BOUSCAT", "Hippodrome", ""),
            "Hopital Haut Leveque" to listOf("PESSAC", "Hôpital Haut Levêque", ""),
            "Hopital Pellegrin" to listOf("BORDEAUX", "Hôpital Pellegrin", ""),
            "Hôpital Pellegrin" to listOf("BORDEAUX", "Hôpital Pellegrin", ""),
            //I
            "Issac" to listOf("SAINT MÉDARD EN JALLES", "Issac", ""),
            //J
            "Jardin Botanique" to listOf("BORDEAUX", "Jardin Botanique", ""),
            //L
            "La Boétie" to listOf("LE TAILLAN", "La Boétie", ""),
            "La Boétie via HOTA" to listOf("LE TAILLAN", "La Boétie", "via Hontane"),
            "La Cite Du Vin" to listOf("BORDEAUX", "La Cité du Vin", ""),
            "La Cité du Vin" to listOf("BORDEAUX", "La Cité du Vin", ""),
            "La Morlette" to listOf("CENON", "La Morlette", ""),
            "Lauriers" to listOf("LORMONT", "Lauriers", ""),
            "La Vache" to listOf("BRUGES", "La Vache", ""),
            "Le Haillan Rostand" to listOf("LE HAILLAN", "Rostand", ""),
            "Les Aubiers" to listOf("BORDEAUX", "Les Aubiers", ""),
            "Les Pins" to listOf("MARTIGNAS SUR JALLE", "Les Pins", ""),
            "Les Pins via IMA" to listOf("MARTIGNAS SUR JALLE", "Les Pins", "via IMA"),
            "Les Sources" to listOf("EYSINES", "Les Sources", ""),
            "Lycee Vaclav Havel" to listOf("BÈGLES", "Lycée Vàclav Havel", ""),
            //M
            "Magonty" to listOf("PESSAC", "Magonty", ""),
            "Mairie Du Bouscat" to listOf("LE BOUSCAT", "Mairie", ""),
            "Malartic" to listOf("GRADIGNAN", "Malartic", ""),
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
            "Parabelle" to listOf("AMBARÈS", "Parabelle", ""),
            "Parabelle via PA" to listOf("AMBARÈS", "Parabelle", "via Pont d'Aquitaine"),
            "Peixotto" to listOf("TALENCE", "Peixotto", ""),
            "Pessac Centre" to listOf("PESSAC", "Centre", ""),
            "Picot" to listOf("EYSINES", "Picot", ""),
            "Pin Galant" to listOf("MERIGNAC", "Pin Galant", ""),
            "Piscine Chambéry" to listOf("VILLENAVE D'ORNON", "Piscine Chambéry", ""),
            "Place De La Bourse" to listOf("BORDEAUX", "Place de la Bourse", ""),
            "Porte De Bourgogne" to listOf("BORDEAUX", "Porte de Bourgogne", ""),
            //Q
            "Quarante Journaux" to listOf("BORDEAUX", "Quarante Journaux", ""),
            "Quatre Chemins" to listOf("MERIGNAC", "Quatre Chemins", ""),
            "Quinconces" to listOf("BORDEAUX", "Quinconces", ""),
            "Quinconces Fleuve" to listOf("BORDEAUX", "Quinconces Fleuve", ""),
            "Quinconces via HOTA" to listOf("BORDEAUX", "Quinconces", "via Hontane"),
            "Quinconces via LSMED" to listOf("BORDEAUX", "Quinconces", "via Lycée Sud Médoc"),
            "Quinsus" to listOf("AMBARÈS", "Quinsus", ""),
            "Quinsus via PA" to listOf("AMBARÈS", "Quinsus", "via Pont d'Aquitaine"),
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
            "Village 5" to listOf("GRADIGNAN", "Village 5", ""),
            "Villenave Pyrénées" to listOf("VILLENAVE D'ORNON", "Pyrénées", ""),
            "Villepreux" to listOf("SAINT AUBIN", "Villepreux", ""),
            "Villepreux via LSMED" to listOf("SAINT AUBIN", "Villepreux", "via Lycée Sud Médoc"),
        )
    }
}