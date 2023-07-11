package com.rundgrun.eco.data.datasources

import android.content.Context
import com.rundgrun.eco.data.exceptions.TokenException

private const val DATA = "data"
private const val TOKEN = "token"

class TokenLocalDataSource(private val context: Context) {

    fun getToken(): String {
        val sharedPreferences =
            context.getSharedPreferences(DATA, Context.MODE_PRIVATE) ?: throw IllegalStateException()
        val token = sharedPreferences.getString(TOKEN, null)
        return if (token != null) {
            return token
        } else {
            throw TokenException()
        }
    }

    fun saveToken(token: String) {
        val sharedPreferences =
            context.getSharedPreferences(DATA, Context.MODE_PRIVATE)?: throw IllegalStateException()
        sharedPreferences.edit().putString(TOKEN, token).apply()
    }
}