package com.task.data.dto.news

/**
 * Created by eldhosepaul on 13/07/23.
 */
data class NewsDataClass(
    var articles: List<NewsItem>,
    var status: String,
    var totalResults: Int
)

