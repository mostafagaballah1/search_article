package com.task.articles_search.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.articles_search.data.ArticlesRepository.Companion.SEARCHED_ARTICLE
import com.task.articles_search.model.Article
import com.task.articles_search.ui.fragments.articles.ArticlesType
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article WHERE title LIKE :query")
    fun articlesByQuery(query: String): PagingSource<Int, Article>

    @Query("SELECT * FROM article WHERE type = :type")
    fun articlesByType(type: Int): PagingSource<Int, Article>

    @Query("SELECT * FROM article WHERE type = :type")
    fun articlesByTypeAsFlow(type: Int): Flow<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<Article>)

    @Query("DELETE FROM article WHERE type = :type")
    suspend fun clearArticles(type: Int = SEARCHED_ARTICLE)
}