package com.example.feedapp.networking.service

import com.example.feedapp.data.model.RssFeed
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface RssService {
    @GET
    fun fetchRssFeed(@Url url: String): Call<RssFeed>
}