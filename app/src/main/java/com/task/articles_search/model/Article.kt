package com.task.articles_search.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class Article(
    @PrimaryKey val id: String = "",
    val title: String? = "",
    val pubDate: String? = "",
    val type: Int? = 0,
)
