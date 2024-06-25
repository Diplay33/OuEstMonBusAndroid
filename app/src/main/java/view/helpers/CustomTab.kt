package view.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

fun openTab(url: String, context: Context) {
    val packageName = "com.android.chrome"
    val activity = (context as? Activity)
    val builder = CustomTabsIntent.Builder()

    builder.setShowTitle(true)
    builder.setInstantAppsEnabled(true)

    val customBuilder = builder.build()
    if(isPackageInstalled(context, packageName)) {
        customBuilder.intent.setPackage(packageName)
        customBuilder.launchUrl(context, Uri.parse(url))
    } else {
        val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        activity?.startActivity(i)
    }
}

fun isPackageInstalled(context: Context, packageName: String?): Boolean {
    var result = false
    try {
        context.packageManager.getPackageInfo(packageName!!, PackageManager.GET_ACTIVITIES)
        result = true
    } catch (_: PackageManager.NameNotFoundException) { }
    return result
}