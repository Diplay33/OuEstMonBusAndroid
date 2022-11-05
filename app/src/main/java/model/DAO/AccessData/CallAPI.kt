package model.DAO.AccessData

import okhttp3.*
import java.io.IOException

class CallAPI {
    companion object {
        fun run(url: String, callback: (String) -> Unit) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if(!response.isSuccessful) throw IOException("Unexpected code $response")

                        for((name, value) in response.headers) {
                            println("$name: $value")
                        }

                        callback(response.body!!.string())
                    }
                }
            })
        }
    }
}