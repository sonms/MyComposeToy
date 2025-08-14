package com.example.mycomposetoy.domain.repository

import com.example.mycomposetoy.domain.entity.PaginatedUserListEntity
import com.example.mycomposetoy.domain.entity.UserDetailEntity

interface UserListRepository {
    suspend fun getUserList(page: Int) : Result<PaginatedUserListEntity>

    suspend fun getUserDetail(id: Int) : Result<UserDetailEntity>
}