package com.rundgrun.eco.domain.usecase

import com.rundgrun.eco.domain.repositories.TokenRepository

class UpdateTokenUseCase(
    val repository: TokenRepository
) {
    fun execute(){
        repository.updateToken()
    }
}