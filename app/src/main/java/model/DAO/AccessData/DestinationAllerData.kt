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
            67 to listOf(listOf("AMBARÈS", "Parabelle")),
            //Lianes 8
            6 to listOf(listOf("GRADIGNAN", "Centre")),
            //Lianes 9
            7 to listOf(listOf("BORDEAUX", "Brandenburg")),
            //Lianes 15
            12 to listOf(listOf("BORDEAUX", "Centre Commercial du Lac")),
            //Lianes 16
            13 to listOf(listOf("MERIGNAC", "Les Pins")),
            //Ligne 20
            17 to listOf(listOf("MERIGNAC", "Cimetière Intercommunal")),
            //Ligne 21
            71 to listOf(listOf("GRADIGNAN", "Beausoleil")),
            //Ligne 22
            72 to listOf(listOf("PAREMPUYRE", "Fontanieu")),
            //Ligne 23
            19 to listOf(listOf("LE BOUSCAT", "Hippodrome")),
            //Ligne 24
            20 to listOf(listOf("PESSAC", "Romainville")),
            //Ligne 25
            73 to listOf(
                listOf("BOULIAC", "Centre Commercial"),
                listOf("BOULIAC", "La Belle Étoile")
            ),
            //Ligne 26
            22 to listOf(listOf("MARTIGNAS SUR JALLE", "Les Pins")),
            //Ligne 27
            23 to listOf(listOf("LORMONT", "Buttinière")),
            //Ligne 28
            24 to listOf(listOf("BORDEAUX", "Galin")),
            //Ligne 29
            25 to listOf(
                listOf("AMBARÈS", "Bernatet"),
                listOf("SAINT LOUIS DE MONTFERRAND", "Belle Rive")
            ),
            //Ligne 30
            74 to listOf(listOf("SAINT MÉDARD EN JALLES", "Galaxie")),
            //Lianes 31
            26 to listOf(
                listOf("GRADIGNAN", "Beausoleil"),
                listOf("PESSAC", "Cité Photonique")
            ),
            //Ligne 32
            75 to listOf(listOf("BOULIAC", "Centre Commercial")),
            //Ligne 33
            27 to listOf(listOf("BORDEAUX", "Cracovie")),
            //Ligne 34
            28 to listOf(listOf("MERIGNAC", "Les Pins")),
            //Lianes 35
            29 to listOf(listOf("BORDEAUX", "Cracovie")),
            //Ligne 37
            30 to listOf(
                listOf("SAINT AUBIN", "Lycée Sud Médoc"),
                listOf("SAINT AUBIN", "Villepreux")
            ),
            //Ligne 38
            31 to listOf(listOf("BLANQUEFORT", "Caychac")),
            //Lianes 39
            32 to listOf(
                listOf("EYSINES", "Cantinolle"),
                listOf("SAINT MÉDARD EN JALLES", "ZA Picot")
            ),
            //Ligne 51
            84 to listOf(listOf("MERIGNAC", "IMA")),
            //Ligne 52
            85 to listOf(listOf("PAREMPUYRE", "Fontanieu")),
            //Ligne 53
            134 to listOf(listOf("AMBÈS", "Escarraguel")),
            //Ligne 55
            87 to listOf(listOf("PESSAC", "ZA Magellan")),
            //Ligne 57
            89 to listOf(listOf("AMBARÈS", "Centre")),
            //TBNight
            41 to listOf(listOf("GRADIGNAN", "Village 5")),
            //Ligne 60
            135 to listOf(listOf("BASSENS", "La Chênaie")),
            //Ligne 61
            136 to listOf(listOf("AMBÈS", "Escarraguel")),
            //Ligne 64
            47 to listOf(listOf("LORMONT", "Buttinière")),
            //Ligne 65
            137 to listOf(listOf("ARTIGUES PRÈS BORDEAUX", "Tout Y Faut")),
            //Ligne 66
            138 to listOf(listOf("LORMONT", "Lauriers")),
            //Ligne 67
            50 to listOf(listOf("LORMONT", "Buttinière")),
            //Ligne 69
            139 to listOf(listOf("AMBARÈS", "Max Linder")),
            //Ligne 70
            140 to listOf(listOf("EYSINES", "Lycée Charles Peguy")),
            //Ligne 71
            97 to listOf(listOf("LE TAILLAN", "Lycée Sud Médoc")),
            //Ligne 72
            98 to listOf(
                listOf("BRUGES", "Gare"),
                listOf("BRUGES", "Zone de Fret")
            ),
            //Ligne 73
            99 to listOf(listOf("LE BOUSCAT", "Hippodrome")),
            //Ligne 74
            100 to listOf(listOf("GRADIGNAN", "Stade Ornon")),
            //Ligne 75
            101 to listOf(listOf("BRUGES", "Gare")),
            //Ligne 76
            102 to listOf(listOf("PAREMPUYRE", "Landegrand")),
            //Ligne 77
            103 to listOf(listOf("PESSAC", "Candau")),
            //Ligne 78
            104 to listOf(listOf("PESSAC", "Toctoucau")),
            //Ligne 79
            105 to listOf(listOf("BLANQUEFORT", "Ecoparc")),
            //Ligne 80
            106 to listOf(listOf("GRADIGNAN", "Stade Ornon")),
            //Ligne 81
            107 to listOf(listOf("LE TAILLAN", "Stade Municipal")),
            //Ligne 82
            108 to listOf(listOf("LE BOUSCAT", "Hôpital Suburbain")),
            //Ligne 83
            109 to listOf(listOf("SAINT AUBIN", "Pinsoles")),
            //Ligne 84
            110 to listOf(listOf("SAINT MÉDARD EN JALLES", "ZA Picot")),
            //Ligne 85
            111 to listOf(listOf("BÈGLES", "Salengro")),
            //Ligne 86
            112 to listOf(listOf("BÈGLES", "Terres Neuves")),
            //Ligne 87
            58 to listOf(listOf("VILLENAVE D'ORNON", "Piscine Chambéry")),
            //Ligne 89
            114 to listOf(listOf("VILLENAVE D'ORNON", "Pyrénées")),
            //Ligne 90
            115 to listOf(listOf("VILLENAVE D'ORNON", "Gare")),
            //Navette Arena
            128 to listOf(listOf("FLOIRAC", "Arkéa Arena")),
            //Navette Stade
            127 to listOf(listOf("BORDEAUX", "Stade Matmut Atlantique")),
            //BatCUB
            130 to listOf(listOf("LORMONT", "Bas"))
        )
    }
}