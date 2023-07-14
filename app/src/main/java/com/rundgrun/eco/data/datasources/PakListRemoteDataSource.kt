package com.rundgrun.eco.data.datasources

import com.rundgrun.eco.data.Pak
import com.rundgrun.eco.data.exceptions.UpdateTokenException
import com.rundgrun.eco.domain.PakResponseAnalyser
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.util.*

class PakListRemoteDataSource {

    private lateinit var client: OkHttpClient
    private fun getAllPak(token: String) {

                con.setRequestProperty("content-type", "application/json")
                con.requestMethod = "POST"


                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val `in` = BufferedReader(InputStreamReader(con.inputStream))
                    val response = StringBuilder()
                    var inputLine: String?
                    while (`in`.readLine().also { inputLine = it } != null) {
                        response.append(inputLine)
                    }
                    listener.updateAllPAK(PakResponseAnalyser.analyzeAllPak(response.toString()))
                    `in`.close()
                } else {
                    println("request did not work.")
                }
                con.disconnect()
            } catch (e: IOException) {
                println(e.message)
            }
        }
    }

    fun getPakList(token: String): ArrayList<Pak> {
        val body =
            "{\"queries\":[{\"refId\":\"A\",\"datasource\":{\"uid\":\"hqShlfU7k\",\"type\":\"mysql\"},\"rawSql\":\"select \\nCONVERT_TZ(m.created, @@session.time_zone, '+00:00') as \\\"time\\\",\\nc.modem_name,\\nm.state_220 AS \\\"220v\\\",\\n(m.state_modem) AS \\\"modem\\\",\\n(m.state_sensors) AS \\\"sensors\\\",\\nc.district_name,\\nc.post_address,\\nc.point_id,\\nc.note as \\\"Комментарий\\\"\\nfrom points_catalog c \\nleft join points_info m USING(point_id) \\norder by modem_name\\n\",\"format\":\"table\",\"datasourceId\":3,\"intervalMs\":60000,\"maxDataPoints\":1011}],\"range\":{\"from\":\"2023-03-10T04:40:19.903Z\",\"to\":\"2023-03-10T10:40:19.903Z\",\"raw\":{\"from\":\"now-6h\",\"to\":\"now\"}},\"from\":\"1678423219903\",\"to\":\"1678444819903\"}"

        val request = Request.Builder()
            .url("http://10.1.3.85:3000/api/ds/query")
            .addHeader("accept", "application/json, text/plain, */*")
            .addHeader("Accept-Encoding", "gzip, deflate")
            .addHeader("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
            .addHeader("Connection", "keep-alive")
            .addHeader("Cookie", token)
            .addHeader("Host", "10.1.3.85:3000")
            .addHeader("Origin", "http://10.1.3.85:3000")
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

                    }
                }
            }
        } catch (e: IOException) {

        }
        throw UpdateTokenException()
    }
}