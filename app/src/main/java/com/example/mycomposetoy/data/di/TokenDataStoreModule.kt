package com.example.mycomposetoy.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.mycomposetoy.data.datasource.local.TokenDataSource
import com.example.mycomposetoy.data.datasourceimpl.TokenDataSourceImpl
import com.example.mycomposetoy.data.repositoryimpl.TokenRepositoryImpl
import com.example.mycomposetoy.domain.repository.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val TOKEN_PREFERENCES = "token_preferences"
private val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = TOKEN_PREFERENCES)

@Module
@InstallIn(SingletonComponent::class)
object TokenDataStoreModule {
    @Provides
    @Singleton
    fun provideTokenDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.tokenDataStore

    @Provides
    @Singleton
    fun provideTokenDataSource(
        dataStore: DataStore<Preferences>
    ): TokenDataSource = TokenDataSourceImpl(dataStore)

    @Provides
    @Singleton
    fun provideTokenRepository(
        dataSource: TokenDataSource
    ): TokenRepository = TokenRepositoryImpl(dataSource)
}