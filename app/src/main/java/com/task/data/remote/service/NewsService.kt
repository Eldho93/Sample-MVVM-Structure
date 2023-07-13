package com.task.data.remote.service

import com.task.data.dto.news.News
import com.task.data.dto.news.NewsDataClass
import com.task.data.dto.news.NewsItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by Eldho
 */

interface NewsService {
    @GET("everything?q=tesla&from=2023-06-13&sortBy=publishedAt&apiKey=91207942253f46948f3ad80c3612214e")
    suspend fun fetchNews(): Response<News>
}
