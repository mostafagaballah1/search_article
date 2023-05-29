package com.task.articles_search.data


import androidx.paging.Pager
import com.task.articles_search.api.ArticlesService
import com.task.articles_search.api.PopularArticleApiResponse
import com.task.articles_search.api.PopularResponse
import com.task.articles_search.data.ArticlesRepository.Companion.MOST_VIEWED_ARTICLE
import com.task.articles_search.db.AppDatabase
import com.task.articles_search.db.ArticleDao
import com.task.articles_search.model.Article
import com.task.articles_search.ui.fragments.articles.ArticlesType
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class ArticlesRepositoryTest {

    private val mockService = mockk<ArticlesService>(relaxed = true)
    private val mockDatabase = mockk<AppDatabase>(relaxed = true)
    private val mockArticlesDao = mockk<ArticleDao>(relaxed = true)

    private lateinit var instance: ArticlesRepository

    @Before
    fun setUp() {
        every { mockDatabase.articlesDao() } returns mockArticlesDao
        instance = ArticlesRepository(mockService, mockDatabase)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        unmockkAll()
    }

    @Test
    fun fetchPopularArticlesNotEmpty() {
        runBlocking {
            val type = ArticlesType.MostViewed
            val apiResponse = mockk<PopularResponse>(relaxed = true)
            val responseItem = mockk<PopularArticleApiResponse>(relaxed = true)
            val articleItem = mockk<Article>(relaxed = true)
            val responseItems = listOf(responseItem)
            val articleItems = listOf(articleItem)

            coEvery { mockService.mostViewedArticles() } returns apiResponse
            coEvery { apiResponse.items } returns responseItems
            coEvery { responseItem.toArticle(any()) } returns articleItem

            instance.fetchPopularArticles(type)

            coVerify {
                apiResponse.items
                mockArticlesDao.clearArticles(MOST_VIEWED_ARTICLE)
                mockArticlesDao.insertAll(articleItems)
                mockArticlesDao.articlesByTypeAsFlow(MOST_VIEWED_ARTICLE)
            }
        }
    }

    @Test
    fun fetchPopularArticlesEmpty() {
        runBlocking {
            val type = ArticlesType.MostViewed
            val apiResponse = mockk<PopularResponse>(relaxed = true)
            val responseItems = emptyList<PopularArticleApiResponse>()

            coEvery { mockService.mostViewedArticles() } returns apiResponse
            coEvery { apiResponse.items } returns responseItems

            instance.fetchPopularArticles(type)

            coVerify {
                apiResponse.items
                mockArticlesDao.articlesByTypeAsFlow(MOST_VIEWED_ARTICLE)
            }
            coVerify(exactly = 0) {
                mockArticlesDao.clearArticles(any())
                mockArticlesDao.insertAll(any())
            }
        }
    }

    @Test
    fun searchArticle() {

        val query = "Android"
        val mockPager = mockk<Pager<Int, Article>>(relaxed = true)

        mockkConstructor(Pager::class)
        every { anyConstructed<Pager<Int, Article>>().flow } returns mockPager.flow

        instance.searchArticle(query)

        verify { mockPager.flow }
    }
}