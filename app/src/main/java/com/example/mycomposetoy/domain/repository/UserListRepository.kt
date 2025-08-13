package com.example.mycomposetoy.domain.repository

import com.example.mycomposetoy.domain.entity.PaginatedUserListEntity

interface UserListRepository {
    suspend fun getUserList(page: Int) : Result<PaginatedUserListEntity>
}