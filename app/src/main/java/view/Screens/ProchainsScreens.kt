package view.Screens

sealed class ProchainsScreens(val route: String) {
    object SearchLineView: ProchainsScreens(route = "search_line_view")
    object SearchStopList: ProchainsScreens(route = "search_stop_list")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}