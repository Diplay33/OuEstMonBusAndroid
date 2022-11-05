package view.lines_map_list

sealed class Screens(val route: String) {
    object MapListMain: Screens(route = "map_list_main")
    object HelloWorld: Screens(route = "hello_world")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
