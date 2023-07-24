package com.rundgrun.eco.data.datasources

import android.content.Context
import com.rundgrun.eco.domain.exceptions.AccountException
import com.rundgrun.eco.domain.models.Account


private const val DATA = "data"
private const val LOGIN = "login"
private const val PASSWORD = "password"

class AccountLocalDataSource(private val context: Context) {

    fun getAccount(): Account {
        val sharedPreferences =
            context.getSharedPreferences(DATA, Context.MODE_PRIVATE) ?: throw IllegalStateException()
        val login = sharedPreferences.getString(LOGIN, null)
        val password = sharedPreferences.getString(PASSWORD, null)
        return if (login != null && password != null) {
            return Account(login, password)
        } else {
            throw AccountException()
        }
    }

    fun saveAccount(account: Account) {
        val sharedPreferences =
            context.getSharedPreferences(DATA, Context.MODE_PRIVATE)?: throw IllegalStateException()
        sharedPreferences.edit().putString(LOGIN, account.login).apply()
        sharedPreferences.edit().putString(PASSWORD, account.password).apply()
    }

    fun removeAccount(){
        val sharedPreferences =
            context.getSharedPreferences(DATA, Context.MODE_PRIVATE)?: throw IllegalStateException()
        sharedPreferences.edit().remove(LOGIN).apply()
        sharedPreferences.edit().remove(PASSWORD).apply()
    }
}