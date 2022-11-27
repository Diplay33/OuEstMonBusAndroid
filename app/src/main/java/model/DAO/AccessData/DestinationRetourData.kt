package model.DAO.AccessData

class DestinationRetourData {
    companion object {
        val destinations: Map<Int, List<List<String>>> = mapOf(
            //Tram A
            59 to listOf(
                listOf("BASSENS/CARBON BLANC", "La Gardette"),
                listOf("FLOIRAC", "Dravemont")
            ),
            //Tram B
            60 to listOf(
                listOf("PESSAC", "Centre"),
                listOf("PESSAC", "France Alouette")
            ),
            //Tram C
            61 to listOf(
                listOf("BÈGLES", "Gare"),
                listOf("VILLENAVE D'ORNON", "Pyrénées")
            ),
            //Tram D
            62 to listOf(
                listOf("BORDEAUX", "Carle Vernet")
            ),
            //Lianes 1
            1 to listOf(listOf("BORDEAUX", "Gare Saint Jean")),
            //Lianes 2
            2 to listOf(listOf("BORDEAUX", "Quinconces")),
            //Lianes 3
            3 to listOf(listOf("BORDEAUX", "Quinconces")),
            //Lianes 4
            4 to listOf(listOf("BORDEAUX", "Bassins à Flot")),
            //Lianes 5
            5 to listOf(listOf("VILLENAVE D'ORNON", "Piscine Chambéry")),
            //Lianes 7
            67 to listOf(listOf("BORDEAUX", "Centre Commercial du Lac")),
            //Lianes 8
            6 to listOf(listOf("BORDEAUX", "Hôpital Pellegrin")),
            //Lianes 9
            7 to listOf(listOf("BORDEAUX", "Gare Saint Jean")),
            //Lianes 10
            8 to listOf(listOf("BORDEAUX", "Jardin Botanique")),
            //Lianes 11
            9 to listOf(listOf("VILLENAVE D'ORNON", "Courréjean")),
            //Lianes 12
            10 to listOf(listOf("BORDEAUX", "Palais de Justice")),
            //Lianes 15
            12 to listOf(
                listOf("VILLENAVE D'ORNON", "Pont de la Maye"),
                listOf("VILLENAVE D'ORNON", "Courréjean")
            ),
            //Lianes 16
            13 to listOf(listOf("BOULIAC", "Centre Commercial")),
            //Ligne 20
            17 to listOf(listOf("BORDEAUX", "Victoire")),
            //Ligne 21
            71 to listOf(listOf("TALENCE", "Peixotto")),
            //Ligne 22
            72 to listOf(listOf("BLANQUEFORT", "Frankton")),
            //Ligne 23
            19 to listOf(listOf("MERIGNAC", "Fontaine d'Arlac")),
            //Ligne 24
            20 to listOf(listOf("BORDEAUX", "République")),
            //Ligne 25
            73 to listOf(listOf("BORDEAUX", "Parc des Expositions")),
            //Ligne 26
            22 to listOf(listOf("BÈGLES", "Terres Neuves")),
            //Ligne 27
            23 to listOf(listOf("BORDEAUX", "Stalingrad")),
            //Ligne 28
            24 to listOf(listOf("BORDEAUX", "Stalingrad")),
            //Ligne 29
            25 to listOf(listOf("BORDEAUX", "Préfecture")),
            //Ligne 30
            74 to listOf(listOf("MERIGNAC", "Cimetière Intercommunal")),
            //Corol 31
            26 to listOf(listOf("BASSENS", "Quai Français")),
            //Corol 32
            75 to listOf(listOf("CENON", "Gare")),
            //Corol 33
            27 to listOf(listOf("BORDEAUX", "Cracovie")),
            //Corol 34
            28 to listOf(listOf("BÈGLES", "Rives d'Arcins")),
            //Corol 35
            29 to listOf(listOf("TALENCE", "Peixotto")),
            //Corol 36
            76 to listOf(listOf("VILLENAVE D'ORNON", "Bourg")),
            //Corol 37
            30 to listOf(listOf("BORDEAUX", "Parc des Expositions")),
            //Corol 38
            31 to listOf(listOf("MERIGNAC", "Phare")),
            //Corol 39
            32 to listOf(listOf("PESSAC", "Cité Photonique")),
            //Citéis 40
            33 to listOf(
                listOf("CENON", "Beausite"),
                listOf("LORMONT", "Lauriers")
            ),
            //Citéis 41
            34 to listOf(listOf("BORDEAUX", "Pelouse de Douet")),
            //Citéis 42
            35 to listOf(listOf("MERIGNAC", "Place Mondésir")),
            //Citéis 43
            79 to listOf(listOf("TALENCE", "Forum")),
            //Citéis 44
            80 to listOf(listOf("PESSAC", "Unitec")),
            //Citéis 45
            36 to listOf(listOf("LE BOUSCAT", "Place Ravezies")),
            //Citéis 46
            81 to listOf(listOf("BORDEAUX", "Grand Parc")),
            //Flexo 49
            82 to listOf(listOf("AMBARÈS", "Collège Claude Massé")),
            //Flexo 50
            83 to listOf(listOf("BORDEAUX", "Stalingrad")),
            //Flexo 51
            84 to listOf(listOf("BORDEAUX", "Galin")),
            //Flexo 52
            85 to listOf(listOf("BORDEAUX", "Galin")),
            //Flexo 54
            86 to listOf(listOf("PESSAC", "Bougnard")),
            //Flexo 55
            87 to listOf(listOf("SAINT MÉDARD EN JALLES", "Gare Routière")),
            //Flexo 57
            89 to listOf(listOf("BLANQUEFORT", "Frankton")),
            //TBNight
            41 to listOf(listOf("BORDEAUX", "Base sous-marine")),
            //Ligne 64
            47 to listOf(listOf("LORMONT", "Buttinière")),
            //Ligne 67
            50 to listOf(listOf("LORMONT", "Buttinière")),
            //Flexo 68
            95 to listOf(listOf("LORMONT", "Buttinière")),
            //Ligne 71
            97 to listOf(listOf("MERIGNAC", "Henri Barbusse")),
            //Citéis 72
            98 to listOf(listOf("BRUGES", "Gare")),
            //Ligne 73
            99 to listOf(listOf("BRUGES", "Zone de Fret")),
            //Spécifique 74
            100 to listOf(listOf("BORDEAUX", "Lycée Camille Jullian")),
            //Flexo Bouliac
            101 to listOf(listOf("BORDEAUX", "Stalingrad")),
            //Ligne 76
            102 to listOf(listOf("BORDEAUX", "Brandenburg")),
            //Spécifique 77
            103 to listOf(listOf("BLANQUEFORT", "Lycée du Bâtiment")),
            //Spécifique 78
            104 to listOf(listOf("EYSINES", "Le Plateau")),
            //Spécifique 79
            105 to listOf(listOf("EYSINES", "Le Grand Caillou")),
            //Ligne 80
            106 to listOf(listOf("BORDEAUX", "Stalingrad")),
            //Spécifique 81
            107 to listOf(listOf("PESSAC", "Lycée Pape Clément")),
            //Spécifique 82
            108 to listOf(listOf("MERIGNAC", "Lycée Daguin")),
            //Ligne 83
            109 to listOf(listOf("EYSINES", "Cantinolle")),
            //Ligne 84
            110 to listOf(
                listOf("EYSINES", "Cantinolle"),
                listOf("LE HAILLAN", "Rostand")
            ),
            //Spécifique 85
            111 to listOf(listOf("LE TAILLAN", "Lycée Sud Médoc")),
            //Spécifique 86
            112 to listOf(
                listOf("GRADIGNAN", "Malartic"),
                listOf("GRADIGNAN", "Stade Ornon")
            ),
            //Ligne 87
            58 to listOf(listOf("PESSAC", "Centre")),
            //Spécifique 88
            113 to listOf(listOf("MERIGNAC", "Lycée Daguin")),
            //Citéis 89
            114 to listOf(listOf("BÈGLES", "Lycée Vàclav Havel")),
            //Ligne 90
            115 to listOf(listOf("BASSENS", "La Chênaie")),
            //Ligne 91
            116 to listOf(listOf("BORDEAUX", "Stalingrad")),
            //Ligne 92
            117 to listOf(listOf("BORDEAUX", "Stalingrad")),
            //Ligne 93
            118 to listOf(listOf("LORMONT", "Buttinière")),
            //Spécifique 94
            119 to listOf(listOf("LORMONT", "Iris")),
            //Spécifique 95
            120 to listOf(listOf("AMBARÈS", "Collège Claude Massé")),
            //Spécifique 96
            121 to listOf(listOf("LORMONT", "Iris")),
            //Navette Arena
            128 to listOf(listOf("BORDEAUX", "Porte de Bourgogne")),
            //Navette Stade
            127 to listOf(listOf("BORDEAUX", "Brandenburg")),
            //BatCUB
            130 to listOf(listOf("BORDEAUX", "Stalingrad"))
        )
    }
}