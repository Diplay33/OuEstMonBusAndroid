package view.Screens

sealed class ProchainsScreens(val route: String) {
    object SearchLineView: ProchainsScreens(route = "search_line_view")
}