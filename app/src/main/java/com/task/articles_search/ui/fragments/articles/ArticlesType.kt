package com.task.articles_search.ui.fragments.articles

sealed class ArticlesType {
    class Search(val query: String) : ArticlesType()
    object MostViewed : ArticlesType()
    object MostShared : ArticlesType()
    object MostEmailed : ArticlesType()
}