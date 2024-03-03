package view.Screens

sealed class ProchainsScreens(val route: String) {
    object FavoriteStopDetail: ProchainsScreens(route = "favorite_stop_detail")
    object SearchLineView: ProchainsScreens(route = "search_line_view")
    object SearchStopList: ProchainsScreens(route = "search_stop_list")
    object NextLineSchedules: ProchainsScreens(route = "next_line_schedules")
    object LineSchedules: ProchainsScreens(route = "line_schedules")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}