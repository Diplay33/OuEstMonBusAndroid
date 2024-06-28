package view.Screens

sealed class SplashScreens(val route: String) {
    object NetworkPicker: SplashScreens(route = "network_picker")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}