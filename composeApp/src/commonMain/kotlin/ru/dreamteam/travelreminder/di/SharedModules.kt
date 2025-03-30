package ru.dreamteam.travelreminder.di

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import ru.dreamteam.travelreminder.presentation.sing_in.SingInViewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dreamteam.travelreminder.data.local.repository.TravelLocalRepositoryImpl
import ru.dreamteam.travelreminder.data.remoute.repository.AuthRepositoryImpl
import ru.dreamteam.travelreminder.data.remoute.repository.TravelRepositoryImpl
import ru.dreamteam.travelreminder.domen.repository.AuthRepository
import ru.dreamteam.travelreminder.domen.repository.TravelRepository
import ru.dreamteam.travelreminder.domen.use_cases.ChangePasswordByEmailUseCase
import ru.dreamteam.travelreminder.domen.use_cases.SignInByEmailAndPasswordUseCase
import ru.dreamteam.travelreminder.domen.use_cases.SignUpByEmailAndPasswordUseCase
import ru.dreamteam.travelreminder.presentation.change_password.ChangePasswordViewModel
import ru.dreamteam.travelreminder.presentation.sing_up.SignUpViewModel
import ru.dreamteam.travelreminder.presentation.travels_list.TravelsViewModel

val sharedModule = module {
    single { AuthRepositoryImpl(get()) }.bind<AuthRepository>()
    single<TravelRepository>(named("local")) { TravelLocalRepositoryImpl(get()) }
    single<TravelRepository>(named("remote")) { TravelRepositoryImpl(get()) }
    single { SignInByEmailAndPasswordUseCase(get()) }
    single { SignUpByEmailAndPasswordUseCase(get()) }
    single { ChangePasswordByEmailUseCase(get()) }

    viewModel { TravelsViewModel(get(named("remote"))) }
    viewModel { SingInViewModel( get()) }
    viewModel { SignUpViewModel( get()) }
    viewModel { ChangePasswordViewModel( get()) }
}