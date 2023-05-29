package com.task.articles_search.ui.fragments.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.task.articles_search.data.ArticlesRepository
import com.task.articles_search.model.Article
import kotlinx.coroutines.flow.Flow

class ArticlesViewModel(
    private val repository: ArticlesRepository,
    private val type: ArticlesType
) : ViewModel() {

    private var pagingDataFlow: Flow<PagingData<Article>>? = null

    init {
        if (type is ArticlesType.Search) {
            pagingDataFlow = repository
                .searchArticle(type.query)
                .cachedIn(viewModelScope)
        }
    }

    fun getPagingDataFlow() = pagingDataFlow
    suspend fun getDataFlow() = repository.fetchPopularArticles(type)
}