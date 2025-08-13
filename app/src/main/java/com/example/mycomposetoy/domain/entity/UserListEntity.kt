package com.example.mycomposetoy.domain.entity

data class PaginatedUserListEntity(
    val userList : List<UserListEntity>,
    val currentPage: Int,
    val totalPages: Int
)
data class UserListEntity(
    val id : Int,
    val email : String,
    val firstName : String,
    val lastName : String,
    val avatar : String,
)