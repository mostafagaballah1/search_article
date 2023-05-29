package com.task.articles_search.ui.fragments.articles.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.articles_search.model.Article
import com.task.articles_search.ui.fragments.articles.viewholders.ArticleViewHolder

class ArticlesAdapter(private val data: List<Article>) : RecyclerView.Adapter<ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(data[position])
    }
}