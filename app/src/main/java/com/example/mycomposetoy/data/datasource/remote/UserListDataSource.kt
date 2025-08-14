package com.example.mycomposetoy.data.datasource.remote

import com.example.mycomposetoy.data.service.UserService
import javax.inject.Inject

class UserListDataSource @Inject constructor(
    private val service: UserService,
) {
    suspend fun getUserList(page: Int) = service.getUserList(page)

    suspend fun getUserDetail(id : Int) = service.getUserDetail(id)
}