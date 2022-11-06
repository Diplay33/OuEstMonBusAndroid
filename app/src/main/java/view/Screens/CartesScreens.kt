package view.Screens

sealed class CartesScreens(val route: String) {
    //object MapListMain: Screens(route = "map_list_main")
    object HelloWorld: CartesScreens(route = "hello_world")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
