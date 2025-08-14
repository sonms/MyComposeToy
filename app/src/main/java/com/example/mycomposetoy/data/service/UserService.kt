package com.example.mycomposetoy.data.service

import com.example.mycomposetoy.data.dto.remote.response.BaseListResponse
import com.example.mycomposetoy.data.dto.remote.response.user.UserListDto
import com.example.mycomposetoy.data.dto.remote.response.userdetail.UserDetailDto
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("users")
    suspend fun getUserList(
        @Query("page") page: Int,
    ) : BaseListResponse<UserListDto>

    @GET("users")
    suspend fun getUserDetail(
        @Query("id") id: Int,
    ) : UserDetailDto
}