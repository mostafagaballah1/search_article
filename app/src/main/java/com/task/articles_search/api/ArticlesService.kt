package com.task.articles_search.api

import com.task.articles_search.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticlesService {

    @GET("svc/search/v2/articlesearch.json")
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("page") page: Int,
    ): SearchResponse

    @GET("svc/mostpopular/v2/emailed/7.json")
    suspend fun mostEmailedArticles(): PopularResponse

    @GET("svc/mostpopular/v2/viewed/7.json")
    suspend fun mostViewedArticles(): PopularResponse

    @GET("svc/mostpopular/v2/shared/7.json")
    suspend fun mostSharedArticles(): PopularResponse


    companion object {

        private val interceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url

            val modifiedUrl = originalUrl.newBuilder()
                .addQueryParameter("api-key", BuildConfig.API_KEY)
                .build()

            val modifiedRequest = originalRequest.newBuilder()
                .url(modifiedUrl)
                .build()

            chain.proceed(modifiedRequest)
        }

        private const val BASE_URL = "https://api.nytimes.com/"

        fun create(): ArticlesService {
            val logger = HttpLoggingInterceptor()
            logger.level = Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(interceptor)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ArticlesService::class.java)
        }
    }
}
