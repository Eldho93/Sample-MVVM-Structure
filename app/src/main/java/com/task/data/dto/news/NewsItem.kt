package com.task.data.dto.news


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@JsonClass(generateAdapter = false)
@Parcelize
data class NewsItem(
        @Json(name = "author")
        var author: String?="",
        @Json(name = "content")
        var content: String?="",
        @Json(name = "description")
        var description: String?="",
        @Json(name = "publishedAt")
        var publishedAt: String?="",
        @Json(name = "title")
        var title: String?="",
        @Json(name = "url")
        var url: String?="",
        @Json(name = "urlToImage")
        var urlToImage: String?=""

) : Parcelable

data class Source(
        @Json(name = "id")
        var id: String?=null,
        @Json(name = "name")
        var name: String?=""
)
