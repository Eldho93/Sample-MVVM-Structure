package com.task.data.remote

import com.task.data.Resource
import com.task.data.dto.news.News

/**
 * Created by Eldho
 */

internal interface RemoteDataSource {
    suspend fun requestRecipes(): Resource<News>
}
