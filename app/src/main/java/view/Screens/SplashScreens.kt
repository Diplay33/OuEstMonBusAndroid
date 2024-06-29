package view.Screens

sealed class SplashScreens(val route: String) {
    object WhatsNew: SplashScreens(route = "whats_new")
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