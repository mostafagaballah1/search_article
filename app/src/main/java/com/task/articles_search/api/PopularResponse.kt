package com.task.articles_search.api

import com.google.gson.annotations.SerializedName
import com.task.articles_search.model.Article


data class PopularResponse(
    @SerializedName("results") val items: List<PopularArticleApiResponse> = emptyList()
)

data class PopularArticleApiResponse(
    @field:SerializedName("id") val id: String = "",
    @field:SerializedName("title") val title: String? = "",
    @field:SerializedName("published_date") val pubDate: String? = "",
) {
    fun toArticle(type: Int) = Article(id = id, title = title, pubDate = pubDate, type = type)
}