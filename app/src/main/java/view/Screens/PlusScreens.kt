package view.Screens

sealed class PlusScreens/*(val route: String)*/ {
    object AllServicesList: CartesScreens(route = "all_services_list")
    object ServiceDetail: CartesScreens(route = "service_detail")

    /*fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }*/
}