package model.DAO.AccessData

class DestinationAllerData {
    companion object {
        val destinations: Map<Int, List<List<String>>> = mapOf(
            //Tram A
            59 to listOf(
                listOf("MERIGNAC", "Aéroport"),
                listOf("LE HAILLAN", "Rostand")
            ),
            //Tram B
            60 to listOf(
                listOf("BORDEAUX", "Berges de la Garonne"),
                listOf("BORDEAUX", "Claveau")
            ),
            //Tram C
            61 to listOf(
                listOf("BORDEAUX", "Parc des Expositions"),
                listOf("BLANQUEFORT", "Gare")
            ),
            //Tram D
            62 to listOf(
                listOf("EYSINES", "Cantinolle"),
                listOf("EYSINES", "Hippodrome")
            ),
            //Bus Express G
            199 to listOf(
                listOf("SAINT AUBIN", "Villepreux"),
                listOf("SAINT MÉDARD EN JALLES", "Issac")
            ),
            //Lianes 1
            1 to listOf(listOf("MERIGNAC", "Beaudésert")),
            //Lianes 2
            2 to listOf(listOf("LE TAILLAN", "La Boétie")),
            //Lianes 3
            3 to listOf(
                listOf("SAINT MÉDARD EN JALLES", "Issac"),
                listOf("SAINT AUBIN", "Villepreux")
            ),
            //Lianes 4
            4 to listOf(listOf("PESSAC", "Cap de Bos")),
            //Lianes 5
            5 to listOf(listOf("BORDEAUX", "Bassins à Flot")),
            //Lianes 7
            7 to listOf(listOf("AMBARÈS", "Parabelle")),
            //Lianes 8
            8 to listOf(listOf("GRADIGNAN", "Centre")),
            //Lianes 9
            9 to listOf(listOf("BORDEAUX", "Brandenburg")),
            //Lianes 15
            15 to listOf(listOf("BORDEAUX", "Centre Commercial du Lac")),
            //Lianes 16
            16 to listOf(listOf("MERIGNAC", "Les Pins")),
            //Ligne 20
            20 to listOf(listOf("MERIGNAC", "Cimetière Intercommunal")),
            //Ligne 21
            64 to listOf(listOf("GRADIGNAN", "Beausoleil")),
            //Ligne 22
            65 to listOf(listOf("PAREMPUYRE", "Fontanieu")),
            //Ligne 23
            21 to listOf(listOf("LE BOUSCAT", "Hippodrome")),
            //Ligne 24
            22 to listOf(listOf("PESSAC", "Romainville")),
            //Ligne 25
            23 to listOf(
                listOf("BOULIAC", "Centre Commercial"),
                listOf("BOULIAC", "La Belle Étoile")
            ),
            //Ligne 26
            66 to listOf(listOf("MARTIGNAS SUR JALLE", "Les Pins")),
            //Ligne 27
            24 to listOf(listOf("LORMONT", "Buttinière")),
            //Ligne 28
            25 to listOf(listOf("BORDEAUX", "Galin")),
            //Ligne 29
            67 to listOf(
                listOf("AMBARÈS", "Bernatet"),
                listOf("SAINT LOUIS DE MONTFERRAND", "Belle Rive")
            ),
            //Ligne 30
            68 to listOf(listOf("SAINT MÉDARD EN JALLES", "Galaxie")),
            //Lianes 31
            26 to listOf(
                listOf("GRADIGNAN", "Beausoleil"),
                listOf("PESSAC", "Cité Photonique")
            ),
            //Ligne 32
            27 to listOf(listOf("BOULIAC", "Centre Commercial")),
            //Ligne 33
            69 to listOf(listOf("BORDEAUX", "Cracovie")),
            //Ligne 34
            28 to listOf(listOf("MERIGNAC", "Les Pins")),
            //Lianes 35
            29 to listOf(listOf("BORDEAUX", "Cracovie")),
            //Ligne 37
            31 to listOf(
                listOf("SAINT AUBIN", "Lycée Sud Médoc"),
                listOf("SAINT AUBIN", "Villepreux")
            ),
            //Ligne 38
            70 to listOf(listOf("BLANQUEFORT", "Caychac")),
            //Lianes 39
            71 to listOf(
                listOf("EYSINES", "Cantinolle"),
                listOf("SAINT MÉDARD EN JALLES", "ZA Picot")
            ),
            //Ligne 51
            81 to listOf(listOf("MARTIGNAS SUR JALLE", "Collège Aliénor d'Aquitaine")),
            //Ligne 52
            82 to listOf(listOf("PAREMPUYRE", "Fontanieu")),
            //Ligne 53
            83 to listOf(listOf("AMBÈS", "Escarraguel")),
            //Ligne 55
            44 to listOf(listOf("PESSAC", "ZA Magellan")),
            //Ligne 57
            85 to listOf(listOf("AMBARÈS", "Centre")),
            //TBNight
            46 to listOf(listOf("GRADIGNAN", "Village 5")),
            //Ligne 60
            88 to listOf(listOf("BASSENS", "La Chênaie")),
            //Ligne 61
            89 to listOf(listOf("AMBÈS", "Escarraguel")),
            //Ligne 64
            49 to listOf(listOf("ARTIGUES PRÈS BORDEAUX", "Fontderode")),
            //Ligne 65
            92 to listOf(listOf("ARTIGUES PRÈS BORDEAUX", "Tout Y Faut")),
            //Ligne 66
            50 to listOf(listOf("LORMONT", "Lauriers")),
            //Ligne 67
            51 to listOf(listOf("LORMONT", "Buttinière")),
            //Ligne 69
            95 to listOf(listOf("AMBARÈS", "Max Linder")),
            //Ligne 70
            52 to listOf(listOf("EYSINES", "Lycée Charles Peguy")),
            //Ligne 71
            96 to listOf(listOf("LE TAILLAN", "Lycée Sud Médoc")),
            //Ligne 72
            97 to listOf(
                listOf("BRUGES", "Gare"),
                listOf("BRUGES", "Zone de Fret")
            ),
            //Ligne 73
            53 to listOf(listOf("LE BOUSCAT", "Hippodrome")),
            //Ligne 74
            54 to listOf(listOf("GRADIGNAN", "Stade Ornon")),
            //Ligne 75
            98 to listOf(listOf("BRUGES", "Gare")),
            //Ligne 76
            99 to listOf(listOf("PAREMPUYRE", "Landegrand")),
            //Ligne 77
            100 to listOf(listOf("PESSAC", "Candau")),
            //Ligne 78
            55 to listOf(listOf("PESSAC", "Toctoucau")),
            //Ligne 79
            101 to listOf(listOf("BLANQUEFORT", "Ecoparc")),
            //Ligne 80
            102 to listOf(listOf("GRADIGNAN", "Stade Ornon")),
            //Ligne 81
            103 to listOf(listOf("LE TAILLAN", "Stade Municipal")),
            //Ligne 82
            104 to listOf(listOf("LE BOUSCAT", "Hôpital Suburbain")),
            //Ligne 83
            105 to listOf(listOf("SAINT AUBIN", "Pinsoles")),
            //Ligne 84
            106 to listOf(listOf("SAINT MÉDARD EN JALLES", "ZA Picot")),
            //Ligne 85
            107 to listOf(listOf("BÈGLES", "Salengro")),
            //Ligne 86
            56 to listOf(listOf("BÈGLES", "Terres Neuves")),
            //Ligne 87
            57 to listOf(listOf("VILLENAVE D'ORNON", "Piscine Chambéry")),
            //Ligne 89
            109 to listOf(listOf("VILLENAVE D'ORNON", "Pyrénées")),
            //Ligne 90
            110 to listOf(listOf("VILLENAVE D'ORNON", "Gare")),
            //Navette Arena
            118 to listOf(listOf("FLOIRAC", "Arkéa Arena")),
            //Navette Stade
            117 to listOf(listOf("BORDEAUX", "Stade Matmut Atlantique")),
            //BatCUB
            119 to listOf(listOf("LORMONT", "Bas"))
        )
    }
}