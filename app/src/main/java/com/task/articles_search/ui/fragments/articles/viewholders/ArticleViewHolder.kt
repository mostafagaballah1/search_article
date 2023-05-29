package com.task.articles_search.ui.fragments.articles.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.articles_search.databinding.ListitemArticleBinding
import com.task.articles_search.model.Article

class ArticleViewHolder(private val binding: ListitemArticleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(article: Article) {
        binding.title.text = article.title
        binding.pubDate.text = article.pubDate
    }

    companion object {
        fun create(parent: ViewGroup): ArticleViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListitemArticleBinding.inflate(layoutInflater, parent, false)
            return ArticleViewHolder(binding)
        }
    }
}
