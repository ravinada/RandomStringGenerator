package com.example.randomstringgeneratorapp.data.repository

import com.example.randomstringgeneratorapp.data.datasource.RandomStringDataSource
import com.example.randomstringgeneratorapp.data.models.RandomString
import javax.inject.Inject

class RandomStringRepository @Inject constructor(
    private val dataSource: RandomStringDataSource
) {
    suspend fun fetchRandomString(length: Int): Result<RandomString> =
        dataSource.getRandomString(length)
}