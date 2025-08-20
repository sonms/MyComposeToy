package com.example.mycomposetoy.domain.usecase

import com.example.mycomposetoy.domain.entity.auth.TokenEntity
import com.example.mycomposetoy.domain.repository.AuthRepository
import com.example.mycomposetoy.domain.repository.TokenRepository
import javax.inject.Inject

class PostSignInUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(email: String, password: String) : Result<TokenEntity?> {
        repository.signIn(email, password)
            .mapCatching { tokenEntity ->
                if (tokenEntity != null) {
                    tokenRepository.saveAccessToken(tokenEntity)
                    return Result.success(tokenEntity)
                }
            }
        /*
         * tokenRepository.updateCachedAccessToken(token)
            return Result.success(null)*/
        return Result.success(null)
    }
}