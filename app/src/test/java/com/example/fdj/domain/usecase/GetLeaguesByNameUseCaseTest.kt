package com.example.fdj.domain.usecase

import com.example.fdj.domain.model.League
import com.example.fdj.domain.model.Resource
import com.example.fdj.domain.repository.LeagueRepository
import io.mockk.MockKAnnotations
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException


internal class GetLeaguesByNameUseCaseTest {

    @MockK
    lateinit var repository: LeagueRepository

    @InjectMockKs
    lateinit var useCase: GetLeaguesByNameUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
    }

    @After
    fun teardown() {
        clearMocks(repository)
    }


    @Test
    fun `getFilteredList returns success with filtered list from repository when local leagues are not empty`() =
        runBlocking {
            // Mock the local leagues
            val localLeagues = listOf(
                League(1, "French League 1"),
                League(2, "English Premier League"),
                League(3, "Moroccan Botola 1")
            )

            coEvery { repository.getLeagues() } returns localLeagues

            // Mock the filtered list result
            val filteredList = listOf(
                League(1, "French League 1"),
            )

            // Call the use case
            val query = "Fre"
            val result = useCase.getFilteredList(query)

            // Verify the emitted value
            result.collect { resource ->
                assert(resource is Resource.Success)
                assert((resource as Resource.Success).data == filteredList)
            }

            // Verify the repository methods were called
            coVerify(exactly = 1) { repository.getLeagues() }
            coVerify(exactly = 0) { repository.downloadLeagues() }
        }

    @Test
    fun `getFilteredList returns success with filtered list from repository after downloading when local leagues are empty`() =
        runBlocking {
            // Mock the local leagues
            val localLeagues = listOf(
                League(1, "French League 1"),
                League(2, "English Premier League"),
                League(3, "Moroccan Botola 1")
            )

            // Mock an empty local leagues list
            coEvery { repository.getLeagues() } returns emptyList() andThen localLeagues

            // Mock the filtered list result nch League 1")
            coEvery { repository.downloadLeagues() } just runs

            val filteredList = listOf(
                League(1, "French League 1"),
            )

            //WHEN
            // Call the use case
            val query = "Fre"
            val collectedValues = mutableListOf<Resource<List<League>>>()
            useCase.getFilteredList(query).toList(collectedValues)

            //THEN
            coVerifyOrder {
                repository.getLeagues()
                repository.downloadLeagues()
                repository.getLeagues()
            }
            //assert is Loading is emitted first
            Assert.assertTrue(collectedValues[0] is Resource.Loading)
            //assert data is emitted with emptyList
            Assert.assertTrue(collectedValues[1] is Resource.Success)
            Assert.assertEquals((collectedValues[1] as Resource.Success).data, filteredList)
        }


    @Test
    fun `getFilteredList returns error when an IOException occurs`() = runBlocking {
        // Mock an IOException
        val errorMessage = "Couldn't reach server. Check your internet connection"
        coEvery { repository.getLeagues() } throws IOException(errorMessage)

        // Call the use case
        val query = "A"
        val result = useCase.getFilteredList(query)

        // Verify the emitted value
        result.collect { resource ->
            assert(resource is Resource.Error)
            assert((resource as Resource.Error).message == errorMessage)
        }

        // Verify the repository methods were called§
        coVerify(exactly = 1) { repository.getLeagues() }
        coVerify(exactly = 0) { repository.downloadLeagues() }
    }

    @Test
    fun `getFilteredList returns error when an unexpected throwable occurs`() = runBlocking {
        // Mock an unexpected throwable
        val errorMessage = "An unexpected error"
        coEvery { repository.getLeagues() } throws RuntimeException(errorMessage)

        // Call the use case
        val query = "A"
        val result = useCase.getFilteredList(query)

        // Verify the emitted value
        result.collect { resource ->
            assert(resource is Resource.Error)
            assert((resource as Resource.Error).message == errorMessage)
        }

        // Verify the repository methods were called
        coVerify(exactly = 1) { repository.getLeagues() }
        coVerify(exactly = 0) { repository.downloadLeagues() }
    }

}