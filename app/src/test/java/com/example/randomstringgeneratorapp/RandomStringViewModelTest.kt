package com.example.randomstringgeneratorapp

import com.example.randomstringgeneratorapp.data.models.RandomString
import com.example.randomstringgeneratorapp.domain.usecase.FetchRandomStringUseCase
import com.example.randomstringgeneratorapp.presentation.viewmodel.RandomStringViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RandomStringViewModelTest {

    private lateinit var viewModel: RandomStringViewModel
    private val fetchRandomStringUseCase: FetchRandomStringUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = RandomStringViewModel(fetchRandomStringUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getRandomString - success updates state`() = runTest {
        val dummy = RandomString("abc123", 6, "2024-10-02T07:38:49Z")
        coEvery { fetchRandomStringUseCase(any()) } returns Result.success(dummy)

        viewModel.getRandomString(6)
        advanceUntilIdle()

        assertEquals(listOf(dummy), viewModel.randomStrings.value)
        assertEquals(null, viewModel.error.value)
        assertEquals(false, viewModel.isLoading.value)
    }

    @Test
    fun `getRandomString failure sets error`() = runTest {
        val exception = RuntimeException("Something went wrong")
        coEvery { fetchRandomStringUseCase(any()) } returns Result.failure(exception)

        viewModel.getRandomString(6)
        advanceUntilIdle()

        assertEquals(emptyList<RandomString>(), viewModel.randomStrings.value)
        assertEquals("Something went wrong", viewModel.error.value)
        assertEquals(false, viewModel.isLoading.value)
    }

    @Test
    fun `deleteAll clears all strings`() = runTest {
        val dummy = RandomString("abc", 3, "2024-10-02T07:38:49Z")
        viewModel.getRandomString(3)
        coEvery { fetchRandomStringUseCase(any()) } returns Result.success(dummy)

        viewModel.deleteAll()

        assertTrue(viewModel.randomStrings.value.isEmpty())
    }

    @Test
    fun `deleteItem removes specified string`() = runTest {
        val str1 = RandomString("abc", 3, "2024-10-02T07:38:49Z")
        val str2 = RandomString("xyz", 3, "2024-10-03T08:00:00Z")
        viewModel = RandomStringViewModel(fetchRandomStringUseCase).apply {
            _randomStrings.value = listOf(str1, str2)
        }

        viewModel.deleteItem(str1)
        assertEquals(listOf(str2), viewModel.randomStrings.value)
    }
}
