package model

import okhttp3.*
import java.io.IOException

class CallAPI {
    companion object {
        fun run(url: String, retry: Boolean = false, callback: (String) -> Unit) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                    if(retry) { run(url) { callback(it) } }
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if(!response.isSuccessful) throw IOException("Unexpected code $response")

                        for((name, value) in response.headers) {
                            println("$name: $value")
                        }

                        try {
                            callback(response.body!!.string())
                        }
                        catch(e: Exception) {
                            println("FATAL ERROR: $e")
                        }
                    }
                }
            })
        }
    }
}