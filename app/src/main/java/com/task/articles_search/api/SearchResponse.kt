package com.task.articles_search.api

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import com.task.articles_search.data.ArticlesRepository.Companion.SEARCHED_ARTICLE
import com.task.articles_search.model.Article
import java.text.SimpleDateFormat

data class SearchResponse(
    @SerializedName("response") val response: DocsResponse? = null,
)

data class DocsResponse(
    @SerializedName("docs") val items: List<SearchArticleApiResponse> = emptyList(),
)

data class SearchArticleApiResponse(
    @field:SerializedName("_id") val id: String = "",
    @field:SerializedName("headline") val headline: ArticleHeadLineApiResponse? = null,
    @field:SerializedName("pub_date") val pubDate: String? = "",
) {
    fun toArticle() =
        Article(
            id = id,
            title = headline?.title,
            pubDate = pubDate?.toFormattedDate(),
            type = SEARCHED_ARTICLE
        )

    companion object {
        @SuppressLint("SimpleDateFormat")
        fun String.toFormattedDate(): String? {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            return parser.parse(this)?.let { formatter.format(it) }
        }
    }
}

data class ArticleHeadLineApiResponse(
    @field:SerializedName("main") val title: String? = "",
)