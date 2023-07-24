package com.rundgrun.eco.data.datasources

import com.rundgrun.eco.domain.models.Pak
import com.rundgrun.eco.domain.exceptions.UpdateTokenException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.*

class PakListRemoteDataSource {

    private lateinit var client: OkHttpClient


    fun getPakList(token: String): ArrayList<Pak> {
        val body =
            "{\"queries\":[{\"refId\":\"A\",\"datasource\":{\"uid\":\"hqShlfU7k\",\"type\":\"mysql\"},\"rawSql\":\"select \\nCONVERT_TZ(m.created, @@session.time_zone, '+00:00') as \\\"time\\\",\\nc.modem_name,\\nm.state_220 AS \\\"220v\\\",\\n(m.state_modem) AS \\\"modem\\\",\\n(m.state_sensors) AS \\\"sensors\\\",\\nc.district_name,\\nc.post_address,\\nc.point_id,\\nc.note as \\\"Комментарий\\\"\\nfrom points_catalog c \\nleft join points_info m USING(point_id) \\norder by modem_name\\n\",\"format\":\"table\",\"datasourceId\":3,\"intervalMs\":60000,\"maxDataPoints\":1011}],\"range\":{\"from\":\"2023-03-10T04:40:19.903Z\",\"to\":\"2023-03-10T10:40:19.903Z\",\"raw\":{\"from\":\"now-6h\",\"to\":\"now\"}},\"from\":\"1678423219903\",\"to\":\"1678444819903\"}"

        val requestBody = body.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
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
            .post(requestBody)
            .build()
        try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    val responseBody = response.body
                    println(responseBody)
                }
            }
        } catch (e: IOException) {

        }
        throw UpdateTokenException()
    }
}