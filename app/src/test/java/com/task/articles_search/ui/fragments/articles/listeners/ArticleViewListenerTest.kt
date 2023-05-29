package com.task.articles_search.ui.fragments.articles.listeners

import com.task.articles_search.ui.fragments.articles.adapters.SearchAdapter
import io.mockk.clearAllMocks
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

class ArticleViewListenerTest {

    private val mockkAdapter = mockk<SearchAdapter>(relaxed = true)

    private lateinit var instance: ArticleViewListener

    @Before
    fun setUp() {
        instance = ArticleViewListener(mockkAdapter)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun retry() {
        instance.retry()

        verify { mockkAdapter.retry() }
    }
}