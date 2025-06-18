package com.example.randomstringgeneratorapp.di

import android.content.Context
import com.example.randomstringgeneratorapp.data.datasource.RandomStringContentProvider
import com.example.randomstringgeneratorapp.data.datasource.RandomStringDataSource
import com.example.randomstringgeneratorapp.data.repository.RandomStringRepository
import com.example.randomstringgeneratorapp.domain.usecase.FetchRandomStringUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContentProvider(@ApplicationContext context: Context): RandomStringDataSource =
        RandomStringContentProvider(context)

    @Provides
    fun provideRepository(dataSource: RandomStringDataSource): RandomStringRepository =
        RandomStringRepository(dataSource)

    @Provides
    fun provideUseCase(repository: RandomStringRepository): FetchRandomStringUseCase =
        FetchRandomStringUseCase(repository)
}