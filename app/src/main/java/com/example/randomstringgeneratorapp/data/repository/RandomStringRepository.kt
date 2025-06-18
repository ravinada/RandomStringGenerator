package com.example.randomstringgeneratorapp.data.repository

import com.example.randomstringgeneratorapp.data.datasource.RandomStringDataSource
import com.example.randomstringgeneratorapp.data.models.RandomString

class RandomStringRepository(private val dataSource: RandomStringDataSource) {
    suspend fun fetchRandomString(length: Int): Result<RandomString> =
        dataSource.getRandomString(length)
}