package com.rundgrun.eco.data.datasources

import com.rundgrun.eco.domain.exceptions.UpdateTokenException
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.*


class TokenRemoteDataSource {

    fun updateToken(token: String): String {
        val now = Date().time
        val then = now - 21600000

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://10.1.3.85:3000/api/annotations?from=$then&to=$now&limit=100&matchAny=false&dashboardId=6")
            .addHeader("accept", "application/json, text/plain, */*")
            .addHeader("Accept-Encoding", "gzip, deflate")
            .addHeader("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
            .addHeader("Connection", "keep-alive")
            .addHeader("Cookie", token)
            .addHeader("Host", "10.1.3.85:3000")
            .addHeader(
                "Referer",
                "http://10.1.3.85:3000/d/BV9D9zUnk/monitoring-airnode?orgId=1&refresh=5m"
            )
            .addHeader(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36"
            )
            .addHeader("x-grafana-org-id", "1")
            .build()
        try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    val cookie = response.header("Set-Cookie")
                    if (cookie != null) {
                        return cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()[0]
                    }
                }
            }
        } catch (e: IOException) {

        }
        throw UpdateTokenException()
    }
}