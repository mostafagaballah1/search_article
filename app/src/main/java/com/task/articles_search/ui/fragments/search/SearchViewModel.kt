package com.task.articles_search.ui.fragments.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class SearchViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var currentQuery: String = savedStateHandle.get(LAST_SEARCH_QUERY) ?: INITIAL_QUERY

    fun getCurrentQuery() = currentQuery

    override fun onCleared() {
        savedStateHandle[LAST_SEARCH_QUERY] = currentQuery
        super.onCleared()
    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val INITIAL_QUERY = "Android"
    }
}