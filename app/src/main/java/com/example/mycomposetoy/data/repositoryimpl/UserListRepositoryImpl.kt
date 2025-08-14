package com.example.mycomposetoy.data.repositoryimpl

import com.example.mycomposetoy.data.datasource.remote.UserListDataSource
import com.example.mycomposetoy.domain.entity.PaginatedUserListEntity
import com.example.mycomposetoy.domain.entity.UserDetailEntity
import com.example.mycomposetoy.domain.repository.UserListRepository
import javax.inject.Inject

class UserListRepositoryImpl @Inject constructor(
    private val dataSource: UserListDataSource
) : UserListRepository {
    override suspend fun getUserList(page: Int): Result<PaginatedUserListEntity> = runCatching {
        val response = dataSource.getUserList(page)

        PaginatedUserListEntity(
            userList = response.data.map { it.toDomain() },
            currentPage = response.page,
            totalPages = response.totalPages
        )
    }

    override suspend fun getUserDetail(id: Int): Result<UserDetailEntity> = runCatching {
        dataSource.getUserDetail(id).toDomain()
    }
}