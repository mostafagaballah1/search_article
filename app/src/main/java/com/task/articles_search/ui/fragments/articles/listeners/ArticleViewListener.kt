package com.task.articles_search.ui.fragments.articles.listeners

import com.task.articles_search.ui.fragments.articles.adapters.SearchAdapter

class ArticleViewListener(private val adapter: SearchAdapter) {
    fun retry() {
        adapter.retry()
    }
}