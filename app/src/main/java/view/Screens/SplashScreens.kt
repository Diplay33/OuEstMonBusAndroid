package view.Screens

sealed class SplashScreens(val route: String) {
    object ChooseNetwork: SplashScreens(route = "choose_network")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}