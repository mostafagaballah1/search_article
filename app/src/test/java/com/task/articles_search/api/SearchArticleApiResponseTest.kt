package com.task.articles_search.api

import com.task.articles_search.api.SearchArticleApiResponse.Companion.toFormattedDate
import com.task.articles_search.data.ArticlesRepository.Companion.SEARCHED_ARTICLE
import com.task.articles_search.model.Article
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchArticleApiResponseTest {

    @Test
    fun toArticle() {
        val id = "a"
        val title = "b"
        val headline = ArticleHeadLineApiResponse(title)
        val pubDate = "2023-02-08T10:00:38+0000"
        val formattedDate = pubDate.toFormattedDate()
        val searchResponse = SearchArticleApiResponse(id, headline, pubDate)
        val result = searchResponse.toArticle()
        val expected = Article(id, title, formattedDate, SEARCHED_ARTICLE)
        assertEquals(expected, result)
    }

    @Test
    fun toFormattedDate() {
        val pubDate = "2023-02-08T10:00:38+0000"
        val formattedDate = "2023-02-08"

        val result = pubDate.toFormattedDate()
        assertEquals(formattedDate, result)
    }
}