package com.example.mycomposetoy.domain.usecase

import com.example.mycomposetoy.domain.repository.UserListRepository
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val repository: UserListRepository
) {
    suspend operator fun invoke(id: Int) = repository.getUserDetail(id)
}