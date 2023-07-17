package com.rundgrun.eco.domain.repositories

import com.rundgrun.eco.domain.models.Account

interface AccountRepository {
    fun getAccount(): Account
    fun saveAccount(account: Account)
    fun removeAccount()
}