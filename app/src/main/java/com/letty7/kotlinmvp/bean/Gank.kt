package com.letty7.kotlinmvp.bean

data class Gank(val error: Boolean, val results: List<Result>) {
    data class Result(
            val _id: String,
            val createdAt: String,
            val desc: String,
            val publishedAt: String,
            val source: String,
            val type: String,
            val url: String,
            val used: Boolean,
            val who: String
    )
}