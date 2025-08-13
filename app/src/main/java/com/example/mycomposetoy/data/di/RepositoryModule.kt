package com.example.mycomposetoy.data.di

import com.example.mycomposetoy.data.repositoryimpl.UserListRepositoryImpl
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
}