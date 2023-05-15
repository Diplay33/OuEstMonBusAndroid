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
            1 to listOf(listOf("MERIGNAC", "René Coty")),
            //Lianes 2
            2 to listOf(listOf("LE TAILLAN", "La Boétie")),
            //Lianes 3
            3 to listOf(
                listOf("SAINT MÉDARD EN JALLES", "Issac"),
                listOf("SAINT MÉDARD EN JALLES", "Gare Routière"),
                listOf("SAINT AUBIN", "Villepreux")
            ),
            //Lianes 4
            4 to listOf(
                listOf("PESSAC", "Magonty"),
                listOf("PESSAC", "Cap de Bos")
            ),
            //Lianes 5
            5 to listOf(listOf("BORDEAUX", "Palais de Justice")),
            //Lianes 7
            67 to listOf(
                listOf("AMBARÈS", "Parabelle"),
                listOf("AMBARÈS", "Quinsus")
            ),
            //Lianes 8
            6 to listOf(listOf("GRADIGNAN", "Centre")),
            //Lianes 9
            7 to listOf(listOf("BORDEAUX", "Brandenburg")),
            //Lianes 10
            8 to listOf(listOf("GRADIGNAN", "Beausoleil")),
            //Lianes 11
            9 to listOf(
                listOf("MARTIGNAS SUR JALLE", "Les Pins"),
                listOf("LE HAILLAN", "5 Chemins")
            ),
            //Lianes 12
            10 to listOf(listOf("EYSINES", "Hippodrome")),
            //Lianes 15
            12 to listOf(
                listOf("BORDEAUX", "Centre Commercial du Lac"),
                listOf("BORDEAUX", "Camping International")
            ),
            //Lianes 16
            13 to listOf(listOf("MERIGNAC", "Les Pins")),
            //Ligne 20
            17 to listOf(listOf("TALENCE", "Thouars")),
            //Ligne 21
            71 to listOf(listOf("GRADIGNAN", "Stade Ornon")),
            //Ligne 22
            72 to listOf(listOf("PAREMPUYRE", "Fontanieu")),
            //Ligne 23
            19 to listOf(
                listOf("PESSAC", "Romainville"),
                listOf("PESSAC", "Toctoucau")
            ),
            //Ligne 24
            20 to listOf(listOf("PESSAC", "Bougnard")),
            //Ligne 25
            73 to listOf(listOf("BORDEAUX", "Brandenburg")),
            //Ligne 26
            22 to listOf(listOf("BORDEAUX", "Quinconces")),
            //Ligne 27
            23 to listOf(listOf("LORMONT", "Buttinière")),
            //Ligne 28
            24 to listOf(listOf("BORDEAUX", "Galin")),
            //Ligne 29
            25 to listOf(listOf("EYSINES", "Lycée Charles Peguy")),
            //Ligne 30
            74 to listOf(listOf("SAINT AUBIN", "Lycée Sud Médoc")),
            //Corol 31
            26 to listOf(listOf("GRADIGNAN", "Village 5")),
            //Corol 32
            75 to listOf(listOf("BOULIAC", "Centre Commercial")),
            //Corol 33
            27 to listOf(listOf("MERIGNAC", "Soleil")),
            //Corol 34
            28 to listOf(listOf("MERIGNAC", "Les Pins")),
            //Corol 35
            29 to listOf(listOf("BORDEAUX", "Cracovie")),
            //Corol 36
            76 to listOf(listOf("LE HAILLAN", "Rostand")),
            //Corol 37
            30 to listOf(
                listOf("SAINT AUBIN", "Lycée Sud Médoc"),
                listOf("SAINT AUBIN", "Villepreux")
            ),
            //Corol 38
            31 to listOf(
                listOf("BLANQUEFORT", "Ecoparc"),
                listOf("BLANQUEFORT", "Caychac")
            ),
            //Corol 39
            32 to listOf(listOf("MERIGNAC", "Les Pins")),
            //Citéis 40
            33 to listOf(listOf("LORMONT", "Buttinière")),
            //Citéis 41
            34 to listOf(listOf("LE BOUSCAT", "Hippodrome")),
            //Citéis 42
            35 to listOf(listOf("MERIGNAC", "Centre")),
            //Citéis 43
            79 to listOf(listOf("BÈGLES", "Lycée Vàclav Havel")),
            //Citéis 44
            80 to listOf(listOf("PESSAC", "Candau")),
            //Citéis 45
            36 to listOf(
                listOf("BOULIAC", "Centre Commercial"),
                listOf("BOULIAC", "La Belle Étoile")
            ),
            //Citéis 46
            81 to listOf(listOf("LE BOUSCAT", "Hôpital Suburbain")),
            //Flexo 49
            82 to listOf(listOf("AMBARÈS", "Du Roy")),
            //Flexo 50
            83 to listOf(listOf("AMBARÈS", "René Coty")),
            //Flexo 51
            84 to listOf(listOf("ARTIGUES PRÈS BORDEAUX", "Virecourt")),
            //Flexo 52
            85 to listOf(listOf("BOULIAC", "Vettiner")),
            //Flexo 54
            86 to listOf(listOf("MERIGNAC", "Fontaine d'Arlac")),
            //Flexo 55
            87 to listOf(listOf("SAINT MÉDARD EN JALLES", "Gare Routière")),
            //Flexo 57
            89 to listOf(listOf("PAREMPUYRE", "Libération")),
            //TBNight
            41 to listOf(listOf("GRADIGNAN", "Village 5")),
            //Ligne 64
            47 to listOf(listOf("ARTIGUES PRÈS BORDEAUX", "Fontderode")),
            //Ligne 67
            50 to listOf(listOf("LORMONT", "Buttinière")),
            //Flexo 68
            95 to listOf(listOf("LORMONT", "Buttinière")),
            //Ligne 71
            97 to listOf(listOf("LE TAILLAN", "Lycée Sud Médoc")),
            //Citéis 72
            98 to listOf(listOf("EYSINES", "Cantinolle")),
            //Ligne 73
            99 to listOf(listOf("BORDEAUX", "Parc des Expositions")),
            //Spécifique 74
            100 to listOf(listOf("BORDEAUX", "Gabriel Fauré")),
            //Flexo Bouliac
            101 to listOf(listOf("BOULIAC", "Vettiner")),
            //Ligne 76
            102 to listOf(listOf("PAREMPUYRE", "Landegrand")),
            //Spécifique 77
            103 to listOf(listOf("PAREMPUYRE", "Landegrand")),
            //Spécifique 78
            104 to listOf(listOf("BLANQUEFORT", "La Renney")),
            //Spécifique 79
            105 to listOf(listOf("LE TAILLAN", "Lycée Sud Médoc")),
            //Ligne 80
            106 to listOf(listOf("ARTIGUES PRÈS BORDEAUX", "Tout Y Faut")),
            //Spécifique 81
            107 to listOf(listOf("MARTIGNAS SUR JALLE", "Les Écoles")),
            //Spécifique 82
            108 to listOf(listOf("MARTIGNAS SUR JALLE", "Centre")),
            //Ligne 83
            109 to listOf(listOf("SAINT AUBIN", "Pinsoles")),
            //Ligne 84
            110 to listOf(listOf("SAINT MÉDARD EN JALLES", "ZA Picot")),
            //Spécifique 85
            111 to listOf(listOf("LE TAILLAN", "Tanaïs")),
            //Spécifique 86
            112 to listOf(listOf("GRADIGNAN", "Lycée des Graves")),
            //Ligne 87
            58 to listOf(listOf("VILLENAVE D'ORNON", "Piscine Chambéry")),
            //Spécifique 88
            113 to listOf(listOf("MARTIGNAS SUR JALLE", "Souge")),
            //Citéis 89
            114 to listOf(listOf("BÈGLES", "Lycée Vàclav Havel")),
            //Ligne 90
            115 to listOf(
                listOf("SAINT LOUIS DE MONTFERRAND", "Belle Rive"),
                listOf("CARBON BLANC", "Mouline")
            ),
            //Ligne 91
            116 to listOf(listOf("AMBÈS", "Escarraguel")),
            //Ligne 92
            117 to listOf(listOf("AMBÈS", "Saint Exupéry")),
            //Ligne 93
            118 to listOf(listOf("AMBÈS", "Fort Lajard")),
            //Spécifique 94
            119 to listOf(listOf("AMBARÈS", "Parabelle")),
            //Spécifique 95
            120 to listOf(listOf("AMBARÈS", "Parabelle")),
            //Spécifique 96
            121 to listOf(listOf("SAINT LOUIS DE MONTFERRAND", "Belle Rive")),
            //Navette Arena
            128 to listOf(listOf("FLOIRAC", "Arkéa Arena")),
            //Navette Stade
            124 to listOf(listOf("BORDEAUX", "Stade Matmut Atlantique")),
            //BatCUB
            130 to listOf(listOf("LORMONT", "Bas"))
        )
    }
}