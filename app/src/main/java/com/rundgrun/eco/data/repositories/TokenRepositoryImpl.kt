package com.rundgrun.eco.data.repositories

import com.rundgrun.eco.data.datasources.TokenLocalDataSource
import com.rundgrun.eco.data.datasources.TokenRemoteDataSource
import com.rundgrun.eco.domain.repositories.TokenRepository

class TokenRepositoryImpl : TokenRepository {

    lateinit var tokenLocalDataSource: TokenLocalDataSource
    lateinit var tokenRemoteDataSource: TokenRemoteDataSource

    override fun getToken(): String {
        return tokenLocalDataSource.getToken()
    }

    override fun saveToken(token: String) {
        tokenLocalDataSource.saveToken(token)
    }

    override fun updateToken() {
        tokenLocalDataSource.saveToken(tokenRemoteDataSource.updateToken(tokenLocalDataSource.getToken()))
    }
}