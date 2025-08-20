package com.example.mycomposetoy.data.di

import com.example.mycomposetoy.data.datasource.local.TokenDataSource
import com.example.mycomposetoy.data.datasourceimpl.TokenDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    /*@Binds
    @Singleton
    abstract fun bindTokenDataSource(
        dataSourceImpl: TokenDataSourceImpl
    ): TokenDataSource*/
}