package com.example.mycomposetoy.data.di

import com.example.mycomposetoy.data.repositoryimpl.AuthRepositoryImpl
import com.example.mycomposetoy.data.repositoryimpl.TokenRepositoryImpl
import com.example.mycomposetoy.data.repositoryimpl.UserListRepositoryImpl
import com.example.mycomposetoy.domain.repository.AuthRepository
import com.example.mycomposetoy.domain.repository.TokenRepository
import com.example.mycomposetoy.domain.repository.UserListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindUserListRepository(
        repositoryImpl: UserListRepositoryImpl
    ): UserListRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        repositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}