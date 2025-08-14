package com.example.mycomposetoy.domain.entity

data class UserDetailEntity(
    val data : UserListEntity,
    val support : UserDetailSupportEntity
)

data class UserDetailSupportEntity(
    val url : String,
    val text : String
)
