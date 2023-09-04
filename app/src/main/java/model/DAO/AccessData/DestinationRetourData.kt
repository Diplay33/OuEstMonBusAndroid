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
            62 to listOf(listOf("BORDEAUX", "Carle Vernet")),
            //Lianes 1
            1 to listOf(listOf("BORDEAUX", "Gare Saint Jean")),
            //Lianes 2
            2 to listOf(listOf("BORDEAUX", "Quinconces")),
            //Lianes 3
            3 to listOf(listOf("BORDEAUX", "Quinconces")),
            //Lianes 4
            4 to listOf(listOf("BORDEAUX", "Palais de Justice")),
            //Lianes 5
            5 to listOf(
                listOf("VILLENAVE D'ORNON", "Piscine Chambéry"),
                listOf("VILLENAVE D'ORNON", "Courréjean")
            ),
            //Lianes 7
            67 to listOf(listOf("BORDEAUX", "Centre Commercial du Lac")),
            //Lianes 8
            6 to listOf(listOf("BORDEAUX", "Hôpital Pellegrin")),
            //Lianes 9
            7 to listOf(listOf("BORDEAUX", "Gare Saint Jean")),
            //Lianes 15
            12 to listOf(listOf("VILLENAVE D'ORNON", "Courréjean")),
            //Lianes 16
            13 to listOf(listOf("BOULIAC", "Centre Commercial")),
            //Ligne 20
            17 to listOf(listOf("BORDEAUX", "Brulatour")),
            //Ligne 21
            71 to listOf(listOf("TALENCE", "Peixotto")),
            //Ligne 22
            72 to listOf(listOf("BLANQUEFORT", "Frankton")),
            //Ligne 23
            19 to listOf(listOf("BÈGLES", "Rives d'Arcins")),
            //Ligne 24
            20 to listOf(listOf("BORDEAUX", "Porte de Bourgogne")),
            //Ligne 25
            73 to listOf(listOf("BORDEAUX", "La Cité du Vin")),
            //Ligne 26
            22 to listOf(listOf("MERIGNAC", "Lycée Daguin")),
            //Ligne 27
            23 to listOf(listOf("LE BOUSCAT", "Place Ravezies")),
            //Ligne 28
            24 to listOf(listOf("BORDEAUX", "Stalingrad")),
            //Ligne 29
            25 to listOf(listOf("BASSENS/CARBON BLANC", "La Gardette")),
            //Ligne 30
            74 to listOf(
                listOf("MERIGNAC", "BA 106"),
                listOf("PESSAC", "Centre")
            ),
            //Lianes 31
            26 to listOf(
                listOf("BASSENS", "Quai Français"),
                listOf("AMBARÈS", "Centre"),
                listOf("AMBÈS", "Saint Exupéry")
            ),
            //Ligne 32
            75 to listOf(listOf("BORDEAUX", "Brandenburg")),
            //Ligne 33
            27 to listOf(listOf("MERIGNAC", "Aéroport")),
            //Ligne 34
            28 to listOf(listOf("PESSAC", "Unitec")),
            //Lianes 35
            29 to listOf(listOf("BORDEAUX", "Gare Saint Jean")),
            //Ligne 37
            30 to listOf(listOf("BORDEAUX", "Parc des Expositions")),
            //Ligne 38
            31 to listOf(listOf("MERIGNAC", "Phare")),
            //Lianes 39
            32 to listOf(
                listOf("PESSAC", "Cité Photonique"),
                listOf("VILLENAVE D'ORNON", "Pyrénées")
            ),
            //Ligne 51
            84 to listOf(listOf("BORDEAUX", "Gambetta Mériadeck")),
            //Ligne 52
            85 to listOf(listOf("BLANQUEFORT", "Gare")),
            //Ligne 53
            134 to listOf(listOf("BORDEAUX", "Cracovie")),
            //Ligne 55
            87 to listOf(listOf("BORDEAUX", "République")),
            //Ligne 57
            89 to listOf(listOf("BASSENS/CARBON BLANC", "La Gardette")),
            //TBNight
            41 to listOf(listOf("BORDEAUX", "Base sous-marine")),
            //Ligne 60
            135 to listOf(listOf("BORDEAUX", "Stalingrad")),
            //Ligne 61
            136 to listOf(listOf("BORDEAUX", "Jardin Botanique")),
            //Ligne 64
            47 to listOf(listOf("ARTIGUES PRÈS BORDEAUX", "Le Poteau")),
            //Ligne 65
            137 to listOf(listOf("BORDEAUX", "Stalingrad")),
            //Ligne 66
            138 to listOf(listOf("LORMONT", "Buttinière")),
            //Ligne 67
            50 to listOf(listOf("LORMONT", "Buttinière")),
            //Ligne 69
            139 to listOf(listOf("AMBARÈS", "Quinsus")),
            //Ligne 70
            140 to listOf(listOf("BORDEAUX", "Gambetta Mériadeck")),
            //Ligne 71
            97 to listOf(listOf("MERIGNAC", "Henri Barbusse")),
            //Ligne 72
            98 to listOf(listOf("LE HAILLAN", "Collège Émile Zola")),
            //Ligne 73
            99 to listOf(listOf("BÈGLES", "Rives d'Arcins")),
            //Ligne 74
            100 to listOf(listOf("MERIGNAC", "Fontaine d'Arlac")),
            //Ligne 75
            101 to listOf(listOf("BORDEAUX", "Brandenburg")),
            //Ligne 76
            102 to listOf(listOf("BORDEAUX", "Brandenburg")),
            //Ligne 77
            103 to listOf(listOf("PESSAC", "Bougnard")),
            //Ligne 78
            104 to listOf(listOf("MERIGNAC", "Fontaine d'Arlac")),
            //Ligne 79
            105 to listOf(listOf("BLANQUEFORT", "Gare")),
            //Ligne 80
            106 to listOf(listOf("BORDEAUX", "Palais de Justice")),
            //Ligne 81
            107 to listOf(listOf("EYSINES", "Cantinolle")),
            //Ligne 82
            108 to listOf(listOf("BORDEAUX", "Patinoire")),
            //Ligne 83
            109 to listOf(listOf("EYSINES", "Cantinolle")),
            //Ligne 84
            110 to listOf(
                listOf("LE HAILLAN", "Rostand"),
                listOf("EYSINES", "Cantinolle")
            ),
            //Ligne 85
            111 to listOf(listOf("VILLENAVE D'ORNON", "Collège Chambéry")),
            //Ligne 86
            112 to listOf(listOf("BORDEAUX", "Patinoire")),
            //Ligne 87
            58 to listOf(listOf("PESSAC", "Centre")),
            //Ligne 89
            114 to listOf(listOf("BÈGLES", "Lycée Vàclav Havel")),
            //Ligne 90
            115 to listOf(listOf("BÈGLES", "Lycée Vàclav Havel")),
            //Navette Arena
            128 to listOf(listOf("BORDEAUX", "Porte de Bourgogne")),
            //Navette Stade
            127 to listOf(listOf("CENON", "Gare")),
            //BatCUB
            130 to listOf(listOf("BORDEAUX", "Stalingrad"))
        )
    }
}