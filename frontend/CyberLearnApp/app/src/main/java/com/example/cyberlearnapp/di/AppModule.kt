package com.example.cyberlearnapp.di

import android.content.Context
import com.example.cyberlearnapp.network.ApiService
import com.example.cyberlearnapp.network.RetrofitInstance
import com.example.cyberlearnapp.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return RetrofitInstance.api
    }

    @Provides
    @Singleton
    fun provideUserRepository(@ApplicationContext context: Context): UserRepository {
        return UserRepository(context)
    }
}