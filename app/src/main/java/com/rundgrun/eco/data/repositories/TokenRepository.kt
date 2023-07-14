package com.rundgrun.eco.data.repositories

import com.rundgrun.eco.data.datasources.TokenLocalDataSource
import com.rundgrun.eco.data.datasources.TokenRemoteDataSource

class TokenRepository {

    lateinit var tokenLocalDataSource: TokenLocalDataSource
    lateinit var tokenRemoteDataSource: TokenRemoteDataSource

    fun getToken(): String {
        return tokenLocalDataSource.getToken()
    }

    fun saveToken(token: String) {
        tokenLocalDataSource.saveToken(token)
    }

    fun updateToken() {
        tokenLocalDataSource.saveToken(tokenRemoteDataSource.updateToken(tokenLocalDataSource.getToken()))
    }
}