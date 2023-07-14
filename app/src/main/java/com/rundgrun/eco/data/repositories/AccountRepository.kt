package com.rundgrun.eco.data.repositories

import com.rundgrun.eco.data.datasources.AccountLocalDataSource
import com.rundgrun.eco.domain.models.Account

class AccountRepository {

    lateinit var accountLocalDataSource: AccountLocalDataSource

    fun getAccount(): Account {
        return accountLocalDataSource.getAccount()
    }

    fun saveAccount(account: Account) {
       accountLocalDataSource.saveAccount(account)
    }

    fun removeAccount(){
      accountLocalDataSource.removeAccount()
    }
}