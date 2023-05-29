package com.task.articles_search.api


import com.task.articles_search.data.ArticlesRepository.Companion.MOST_VIEWED_ARTICLE
import com.task.articles_search.model.Article
import org.junit.Assert.assertEquals
import org.junit.Test

class PopularArticleApiResponseTest {

    @Test
    fun toArticle() {
        val id = "a"
        val title = "b"
        val pubDate = "c"
        val popularResponse = PopularArticleApiResponse(id, title, pubDate)
        val result = popularResponse.toArticle(MOST_VIEWED_ARTICLE)
        val expected = Article(id, title, pubDate, MOST_VIEWED_ARTICLE)
        assertEquals(expected, result)
    }
}