package view.Screens

sealed class PlusScreens(val route: String) {
    object AllServicesList: CartesScreens(route = "all_services_list")
}