package com.example.feedapp.ui.viewmodel.di

import com.example.feedapp.domain.repository.WelcomeScreenRepository
import com.example.feedapp.domain.usecase.FetchNewRssUseCase
import com.example.feedapp.ui.viewmodel.feed.CarFeedViewModel
import com.example.feedapp.ui.viewmodel.feed.NewsAndCultureViewModel
import com.example.feedapp.ui.viewmodel.welcome_screen.WelcomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { WelcomeScreenViewModel(repository = get<WelcomeScreenRepository>()) }
    viewModel { CarFeedViewModel(fetchNewRssUseCase = get<FetchNewRssUseCase>()) }
    viewModel { NewsAndCultureViewModel(fetchNewRssUseCase = get<FetchNewRssUseCase>()) }
}