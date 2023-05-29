package com.task.articles_search.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.task.articles_search.api.ArticlesService
import com.task.articles_search.db.AppDatabase
import com.task.articles_search.model.Article
import com.task.articles_search.ui.fragments.articles.ArticlesType
import kotlinx.coroutines.flow.Flow

class ArticlesRepository(
    private val service: ArticlesService,
    private val database: AppDatabase
) {

    suspend fun fetchPopularArticles(type: ArticlesType): Flow<List<Article>> {
        val articleDao = database.articlesDao()

        val typeID = when (type) {
            is ArticlesType.MostViewed -> MOST_VIEWED_ARTICLE
            is ArticlesType.MostShared -> MOST_SHARED_ARTICLE
            is ArticlesType.MostEmailed -> MOST_EMAILED_ARTICLE
            else -> SEARCHED_ARTICLE
        }

        val data = when (type) {
            is ArticlesType.MostViewed -> service.mostViewedArticles().items
            is ArticlesType.MostShared -> service.mostSharedArticles().items
            is ArticlesType.MostEmailed -> service.mostEmailedArticles().items
            else -> emptyList()
        }

        if (data.isNotEmpty()) {
            val articles = data.map { it.toArticle(typeID) }
            articleDao.clearArticles(typeID)
            articleDao.insertAll(articles)
        }

        return articleDao.articlesByTypeAsFlow(typeID)
    }

    @OptIn(ExperimentalPagingApi::class)
    fun searchArticle(query: String): Flow<PagingData<Article>> {

        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { database.articlesDao().articlesByQuery(dbQuery) }

        val pager = Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = ArticlesRemoteMediator(
                query,
                service,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        )

        return pager.flow
    }

    companion object {
        const val SEARCHED_ARTICLE = 0
        const val MOST_VIEWED_ARTICLE = 1
        const val MOST_SHARED_ARTICLE = 2
        const val MOST_EMAILED_ARTICLE = 3

        const val NETWORK_PAGE_SIZE = 10
    }
}
