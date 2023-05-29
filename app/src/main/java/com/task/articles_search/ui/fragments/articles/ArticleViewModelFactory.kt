package com.task.articles_search.ui.fragments.articles

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.task.articles_search.data.ArticlesRepository

class ArticleViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val repository: ArticlesRepository,
    private val type: ArticlesType,
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(ArticlesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArticlesViewModel(repository, type) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
