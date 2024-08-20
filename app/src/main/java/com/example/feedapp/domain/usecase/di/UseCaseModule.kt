package com.example.feedapp.domain.usecase.di

import com.example.feedapp.domain.usecase.FetchNewRssUseCase
import com.example.feedapp.networking.service.RssService
import org.koin.dsl.module

val useCaseModule = module {
    factory<FetchNewRssUseCase> { FetchNewRssUseCase(rssService = get<RssService>()) }
}