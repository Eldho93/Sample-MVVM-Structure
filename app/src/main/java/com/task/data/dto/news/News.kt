package com.task.data.dto.news

data class News(
    var articles: List<NewsItem>,
    var status: String,
    var totalResults: Int
    )
