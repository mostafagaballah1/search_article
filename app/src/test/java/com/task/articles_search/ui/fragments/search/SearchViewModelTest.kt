package com.task.articles_search.ui.fragments.search

import androidx.lifecycle.SavedStateHandle
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchViewModelTest {

    private val mockSavedState = mockk<SavedStateHandle>(relaxed = true)
    private val defaultValue = "Android"

    private lateinit var instance: SearchViewModel

    @Before
    fun setUp() {
        every { mockSavedState.get(any()) ?: defaultValue } returns defaultValue

        instance = SearchViewModel(mockSavedState)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun getCurrentQuery() {
        val result = instance.getCurrentQuery()
        assertEquals(defaultValue, result)
    }
}