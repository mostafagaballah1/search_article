package com.task.articles_search

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.task.articles_search.api.ArticlesService
import com.task.articles_search.data.ArticlesRepository
import com.task.articles_search.db.AppDatabase
import com.task.articles_search.ui.fragments.articles.ArticleViewModelFactory
import com.task.articles_search.ui.fragments.articles.ArticlesType
import com.task.articles_search.ui.fragments.search.SearchViewModelFactory


object Injection {

    private fun provideArticlesRepository(context: Context): ArticlesRepository {
        return ArticlesRepository(ArticlesService.create(), AppDatabase.getInstance(context))
    }

    fun provideArticlesViewModelFactory(context: Context, owner: SavedStateRegistryOwner, type: ArticlesType): ViewModelProvider.Factory {
        return ArticleViewModelFactory(owner, provideArticlesRepository(context), type)
    }
}
