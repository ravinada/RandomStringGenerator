package com.example.randomstringgeneratorapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomstringgeneratorapp.data.models.RandomString
import com.example.randomstringgeneratorapp.domain.usecase.FetchRandomStringUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomStringViewModel @Inject constructor(
    private val fetchRandomStringUseCase: FetchRandomStringUseCase
) : ViewModel() {

    internal val _randomStrings = MutableStateFlow<List<RandomString>>(emptyList())
    val randomStrings: StateFlow<List<RandomString>> = _randomStrings

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun getRandomString(length: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = fetchRandomStringUseCase(length)
            if (result.isSuccess) {
                result.getOrNull()?.let { randomString ->
                    _randomStrings.update { it + randomString }
                }
                _error.value = null
            } else {
                _error.value = result.exceptionOrNull()?.message
            }
            _isLoading.value = false
        }
    }


    fun deleteAll() {
        _randomStrings.value = emptyList()
    }

    fun deleteItem(item: RandomString) {
        _randomStrings.update { it - item }
    }
}