package com.task.data

import com.task.data.dto.news.News
import com.task.data.dto.login.LoginRequest
import com.task.data.dto.login.LoginResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by Eldho
 */

interface DataRepositorySource {
    suspend fun requestRecipes(): Flow<Resource<News>>
    suspend fun doLogin(loginRequest: LoginRequest): Flow<Resource<LoginResponse>>
    suspend fun addToFavourite(id: String): Flow<Resource<Boolean>>
    suspend fun removeFromFavourite(id: String): Flow<Resource<Boolean>>
    suspend fun isFavourite(id: String): Flow<Resource<Boolean>>
}
