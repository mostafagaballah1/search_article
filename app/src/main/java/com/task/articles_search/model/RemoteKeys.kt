package com.task.articles_search.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val articleId: String = "",
    val prevKey: Int? = null,
    val nextKey: Int? = null,
)
