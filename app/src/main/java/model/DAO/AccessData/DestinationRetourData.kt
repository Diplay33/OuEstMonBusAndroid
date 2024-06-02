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
            //Bus Express G
            34 to listOf(listOf("BORDEAUX", "Gare Saint Jean")),
            //Lianes 1
            1 to listOf(listOf("BORDEAUX", "République")),
            //Lianes 2
            2 to listOf(listOf("BORDEAUX", "Quinconces")),
            //Lianes 4
            4 to listOf(listOf("BORDEAUX", "Palais de Justice")),
            //Lianes 5
            5 to listOf(
                listOf("VILLENAVE D'ORNON", "Piscine Chambéry"),
                listOf("VILLENAVE D'ORNON", "Courréjean")
            ),
            //Lianes 7
            7 to listOf(listOf("BORDEAUX", "Centre Commercial du Lac")),
            //Lianes 8
            8 to listOf(listOf("BORDEAUX", "Hôpital Pellegrin")),
            //Lianes 9
            9 to listOf(listOf("BORDEAUX", "Gare Saint Jean")),
            //Lianes 15
            15 to listOf(listOf("VILLENAVE D'ORNON", "Courréjean")),
            //Lianes 16
            16 to listOf(listOf("BOULIAC", "Centre Commercial")),
            //Ligne 20
            20 to listOf(listOf("BORDEAUX", "Brulatour")),
            //Ligne 21
            64 to listOf(listOf("TALENCE", "Peixotto")),
            //Ligne 22
            65 to listOf(listOf("BLANQUEFORT", "Frankton")),
            //Ligne 23
            21 to listOf(listOf("BÈGLES", "Rives d'Arcins")),
            //Ligne 24
            22 to listOf(listOf("BORDEAUX", "Porte de Bourgogne")),
            //Ligne 25
            23 to listOf(listOf("BORDEAUX", "La Cité du Vin")),
            //Ligne 26
            66 to listOf(listOf("MERIGNAC", "Lycée Daguin")),
            //Ligne 27
            24 to listOf(listOf("LE BOUSCAT", "Place Ravezies")),
            //Ligne 28
            25 to listOf(listOf("BORDEAUX", "Stalingrad")),
            //Ligne 29
            67 to listOf(listOf("BASSENS/CARBON BLANC", "La Gardette")),
            //Ligne 30
            68 to listOf(
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
            27 to listOf(listOf("BORDEAUX", "Brandenburg")),
            //Ligne 33
            69 to listOf(listOf("MERIGNAC", "Aéroport")),
            //Ligne 34
            28 to listOf(listOf("PESSAC", "Unitec")),
            //Lianes 35
            29 to listOf(listOf("BORDEAUX", "Gare Saint Jean")),
            //Ligne 37
            31 to listOf(listOf("BORDEAUX", "Parc des Expositions")),
            //Ligne 38
            70 to listOf(listOf("MERIGNAC", "Phare")),
            //Lianes 39
            71 to listOf(
                listOf("PESSAC", "Cité Photonique"),
                listOf("VILLENAVE D'ORNON", "Pyrénées")
            ),
            //Ligne 51
            81 to listOf(listOf("BORDEAUX", "Gambetta Mériadeck")),
            //Ligne 52
            82 to listOf(listOf("BLANQUEFORT", "Gare")),
            //Ligne 53
            83 to listOf(listOf("BORDEAUX", "Cracovie")),
            //Ligne 55
            44 to listOf(listOf("BORDEAUX", "République")),
            //Ligne 57
            85 to listOf(listOf("BASSENS/CARBON BLANC", "La Gardette")),
            //TBNight
            46 to listOf(listOf("BORDEAUX", "Base sous-marine")),
            //Ligne 60
            88 to listOf(listOf("BORDEAUX", "Stalingrad")),
            //Ligne 61
            89 to listOf(listOf("BORDEAUX", "Jardin Botanique")),
            //Ligne 64
            49 to listOf(listOf("LORMONT", "Buttinière")),
            //Ligne 65
            92 to listOf(listOf("BORDEAUX", "Stalingrad")),
            //Ligne 66
            50 to listOf(listOf("LORMONT", "Buttinière")),
            //Ligne 67
            51 to listOf(listOf("LORMONT", "Buttinière")),
            //Ligne 69
            95 to listOf(listOf("AMBARÈS", "Quinsus")),
            //Ligne 70
            52 to listOf(listOf("BORDEAUX", "Gambetta Mériadeck")),
            //Ligne 71
            96 to listOf(listOf("MERIGNAC", "Henri Barbusse")),
            //Ligne 72
            97 to listOf(listOf("LE HAILLAN", "Collège Émile Zola")),
            //Ligne 73
            53 to listOf(listOf("BÈGLES", "Rives d'Arcins")),
            //Ligne 74
            54 to listOf(listOf("MERIGNAC", "Fontaine d'Arlac")),
            //Ligne 75
            98 to listOf(listOf("BORDEAUX", "Brandenburg")),
            //Ligne 76
            99 to listOf(listOf("BORDEAUX", "Brandenburg")),
            //Ligne 77
            100 to listOf(listOf("PESSAC", "Bougnard")),
            //Ligne 78
            55 to listOf(listOf("MERIGNAC", "Fontaine d'Arlac")),
            //Ligne 79
            101 to listOf(listOf("BLANQUEFORT", "Gare")),
            //Ligne 80
            102 to listOf(listOf("BORDEAUX", "Palais de Justice")),
            //Ligne 81
            103 to listOf(listOf("EYSINES", "Cantinolle")),
            //Ligne 82
            104 to listOf(listOf("BORDEAUX", "Patinoire")),
            //Ligne 83
            105 to listOf(listOf("EYSINES", "Cantinolle")),
            //Ligne 84
            106 to listOf(
                listOf("LE HAILLAN", "Rostand"),
                listOf("EYSINES", "Cantinolle")
            ),
            //Ligne 85
            107 to listOf(listOf("VILLENAVE D'ORNON", "Collège Chambéry")),
            //Ligne 86
            56 to listOf(listOf("BORDEAUX", "Patinoire")),
            //Ligne 87
            57 to listOf(listOf("PESSAC", "Centre")),
            //Ligne 89
            109 to listOf(listOf("BÈGLES", "Lycée Vàclav Havel")),
            //Ligne 90
            110 to listOf(listOf("BÈGLES", "Lycée Vàclav Havel")),
            //Navette Arena
            118 to listOf(listOf("BORDEAUX", "Porte de Bourgogne")),
            //Navette Stade
            117 to listOf(listOf("CENON", "Gare")),
            //BatCUB
            119 to listOf(listOf("BORDEAUX", "Stalingrad"))
        )
    }
}