package com.example.randomstringgeneratorapp.data.datasource

import com.example.randomstringgeneratorapp.data.models.RandomString

interface RandomStringDataSource {
    suspend fun getRandomString(length: Int): Result<RandomString>
}