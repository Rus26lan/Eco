package com.rundgrun.eco.data.datasources

import android.content.Context
import com.rundgrun.eco.domain.exceptions.AccountException
import com.rundgrun.eco.domain.exceptions.TokenException
import com.rundgrun.eco.domain.models.Account


private const val ACCOUNT = "account"
private const val LOGIN = "login"
private const val PASSWORD = "password"
private const val TOKEN = "token"

class PreferencesDataSource(private val context: Context) {

    fun getAccount(): Account {
        val sharedPreferences =
            context.getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE) ?: throw IllegalStateException()
        val login = sharedPreferences.getString(LOGIN, null)
        val password = sharedPreferences.getString(PASSWORD, null)
        return if (login != null && password != null) {
            return Account(login, password)
        } else {
            throw AccountException()
        }
    }

    fun saveAccount(param: Account) {
        val sharedPreferences =
            context.getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE)?: throw IllegalStateException()
        sharedPreferences.edit().putString(LOGIN, param.login).apply()
        sharedPreferences.edit().putString(PASSWORD, param.password).apply()
    }

    fun removeAccount(){
        val sharedPreferences =
            context.getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE)?: throw IllegalStateException()
        sharedPreferences.edit().remove(LOGIN).apply()
        sharedPreferences.edit().remove(PASSWORD).apply()
    }



    fun getToken(): String {
        val sharedPreferences =
            context.getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE) ?: throw IllegalStateException()
        val token = sharedPreferences.getString(TOKEN, null)
        return if (token != null) {
            return token
        } else {
            throw TokenException()
        }
    }

    fun saveToken(token: String) {
        val sharedPreferences =
            context.getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE)?: throw IllegalStateException()
        sharedPreferences.edit().putString(TOKEN, token).apply()
    }

}