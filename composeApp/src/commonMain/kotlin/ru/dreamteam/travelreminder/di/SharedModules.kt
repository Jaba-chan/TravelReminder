package ru.dreamteam.travelreminder.di

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import ru.dreamteam.travelreminder.presentation.auth.AuthViewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dreamteam.travelreminder.data.local.repository.TravelLocalRepositoryImpl
import ru.dreamteam.travelreminder.data.remoute.repository.AuthRepositoryImpl
import ru.dreamteam.travelreminder.data.remoute.repository.TravelRepositoryImpl
import ru.dreamteam.travelreminder.domen.repository.AuthRepository
import ru.dreamteam.travelreminder.domen.repository.TravelRepository
import ru.dreamteam.travelreminder.domen.use_cases.SignInWithEmailAndPasswordUseCase

val sharedModule = module {
    single { AuthRepositoryImpl(get()) }.bind<AuthRepository>()
    single<TravelRepository>(named("local")) { TravelLocalRepositoryImpl(get()) }
    single<TravelRepository>(named("remote")) { TravelRepositoryImpl() }
    single { SignInWithEmailAndPasswordUseCase(get()) }

    viewModel { AuthViewModel(get(named("remote")), get()) }
}