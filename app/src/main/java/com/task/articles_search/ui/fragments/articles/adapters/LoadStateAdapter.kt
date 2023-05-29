package com.task.articles_search.ui.fragments.articles.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.task.articles_search.ui.fragments.articles.listeners.ArticleViewListener
import com.task.articles_search.ui.fragments.articles.viewholders.LoadStateViewHolder

class LoadStateAdapter(private val listener: ArticleViewListener) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder.create(parent, listener)
    }
}
