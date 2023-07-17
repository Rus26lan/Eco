package com.rundgrun.eco.data.repositories

import com.rundgrun.eco.data.datasources.AccountLocalDataSource
import com.rundgrun.eco.domain.models.Account
import com.rundgrun.eco.domain.repositories.AccountRepository

class AccountRepositoryImpl : AccountRepository {

    lateinit var accountLocalDataSource: AccountLocalDataSource

    override fun getAccount(): Account {
        return accountLocalDataSource.getAccount()
    }

    override fun saveAccount(account: Account) {
       accountLocalDataSource.saveAccount(account)
    }

    override fun removeAccount(){
      accountLocalDataSource.removeAccount()
    }
}