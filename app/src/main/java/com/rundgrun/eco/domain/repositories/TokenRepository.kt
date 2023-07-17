package com.rundgrun.eco.domain.repositories

interface TokenRepository {
    fun getToken(): String
    fun saveToken(token: String)
    fun updateToken()
}