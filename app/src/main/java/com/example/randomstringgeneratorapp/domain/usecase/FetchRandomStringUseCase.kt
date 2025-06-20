package com.example.randomstringgeneratorapp.domain.usecase

import com.example.randomstringgeneratorapp.data.models.RandomString
import com.example.randomstringgeneratorapp.data.repository.RandomStringRepository
import javax.inject.Inject

class FetchRandomStringUseCase @Inject constructor(
    private val repository: RandomStringRepository
) {
    suspend operator fun invoke(length: Int): Result<RandomString> =
        repository.fetchRandomString(length)
}