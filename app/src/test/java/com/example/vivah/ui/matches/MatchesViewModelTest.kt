package com.example.vivah.ui.matches

import com.example.util.getOrAwaitValue
import com.example.vivah.BaseTest
import com.example.vivah.db.entity.Matches
import com.example.vivah.repository.MatchesRepository
import com.example.vivah.util.Resource
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.inject
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyString

@ExperimentalCoroutinesApi
class MatchesViewModelTest : BaseTest() {

    private val matchesRepository: MatchesRepository by inject()
    private lateinit var viewModel: MatchesViewModel

    @Before
    override
    fun setUp() {
        super.setUp()
        every { matchesRepository.getMatches() } just runs
        viewModel = MatchesViewModel(matchesRepository)
        verify { matchesRepository.getMatches() }
    }

    @Test
    fun getMatches() {
        val expected = Resource.Success<List<Matches>?>(arrayListOf())
        TestCoroutineScope().launch {
            val flow =
                MutableSharedFlow<Resource<List<Matches>?>>(replay = 1).apply { emit(expected) }
            every { matchesRepository.flow } returns flow
        }
        assertEquals(expected, viewModel.getMatches().getOrAwaitValue())
    }

    @Test
    fun updateStatus() {
        val matches = mockkClass(Matches::class)
        every { matches.id } returns anyString()
        viewModel.updateStatus(matches, anyBoolean())
        coVerify { matchesRepository.updateMatchesStatus(anyString(), anyBoolean()) }
    }
}