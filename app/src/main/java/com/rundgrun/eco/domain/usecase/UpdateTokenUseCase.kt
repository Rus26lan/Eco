package com.rundgrun.eco.domain.usecase

import com.rundgrun.eco.domain.repositories.TokenRepository

class UpdateTokenUseCase(
    private val repository: TokenRepository
) {
    fun execute(){
        repository.updateToken()
    }
}