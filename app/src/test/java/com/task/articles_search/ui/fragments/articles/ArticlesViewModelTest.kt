package com.task.articles_search.ui.fragments.articles

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.task.articles_search.data.ArticlesRepository
import com.task.articles_search.model.Article
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ArticlesViewModelTest {

    private val mockRepo = mockk<ArticlesRepository>(relaxed = true)

    private lateinit var instance: ArticlesViewModel

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun getPagingDataFlow() {
        runBlocking {

            val query = "abc"
            val type = ArticlesType.Search(query)
            val mockPagingData = mockk<Flow<PagingData<Article>>>(relaxed = true)
            instance = ArticlesViewModel(mockRepo, type)

            val job = launch(Dispatchers.IO) {

                coEvery { mockRepo.searchArticle(any()).cachedIn(any()) } returns mockPagingData

                val result = instance.getPagingDataFlow()

                coVerify { mockRepo.searchArticle(query).cachedIn(any()) }

                assertEquals(mockPagingData, result)
            }

            job.cancelAndJoin()
        }
    }

    @Test
    fun getDataFlow() {
        runBlocking {

            val type = ArticlesType.MostViewed
            val mockData = mockk<Flow<List<Article>>>(relaxed = true)
            coEvery { mockRepo.fetchPopularArticles(any()) } returns mockData
            instance = ArticlesViewModel(mockRepo, type)

            val job = launch(Dispatchers.IO) {
                val result = instance.getDataFlow()

                coVerify { mockRepo.fetchPopularArticles(type) }

                assertEquals(mockData, result)
            }

            job.cancelAndJoin()

        }
    }
}