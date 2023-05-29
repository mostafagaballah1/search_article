package com.task.articles_search.ui.fragments.articles.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.task.articles_search.databinding.ListitemFooterBinding
import com.task.articles_search.ui.fragments.articles.listeners.ArticleViewListener

class LoadStateViewHolder(
    private val binding: ListitemFooterBinding,
    private val listener: ArticleViewListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { listener.retry() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, listener: ArticleViewListener): LoadStateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListitemFooterBinding.inflate(layoutInflater, parent, false)
            return LoadStateViewHolder(binding, listener)
        }
    }
}
