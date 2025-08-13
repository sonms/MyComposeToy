package com.example.mycomposetoy.domain.usecase

import com.example.mycomposetoy.domain.entity.PaginatedUserListEntity
import com.example.mycomposetoy.domain.repository.UserListRepository
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(
    private val repository: UserListRepository
) {
    suspend operator fun invoke(page: Int) : Result<PaginatedUserListEntity> = repository.getUserList(page)
}