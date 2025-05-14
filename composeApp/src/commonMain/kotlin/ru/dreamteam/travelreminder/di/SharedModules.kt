package ru.dreamteam.travelreminder.di


import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dreamteam.travelreminder.data.local.repository.TravelLocalRepositoryImpl
import ru.dreamteam.travelreminder.data.local.storage.provideFirebaseApiKey
import ru.dreamteam.travelreminder.data.remoute.provideHttpClient
import ru.dreamteam.travelreminder.data.remoute.repository.AuthRepositoryImpl
import ru.dreamteam.travelreminder.data.remoute.repository.MapNavigationRepositoryImpl
import ru.dreamteam.travelreminder.data.remoute.repository.TravelRepositoryImpl
import ru.dreamteam.travelreminder.domen.repository.AuthRepository
import ru.dreamteam.travelreminder.domen.repository.MapNavigationRepository
import ru.dreamteam.travelreminder.domen.repository.TravelLocalRepository
import ru.dreamteam.travelreminder.domen.repository.TravelRepository
import ru.dreamteam.travelreminder.domen.use_cases.AddTravelUseCase
import ru.dreamteam.travelreminder.domen.use_cases.ChangePasswordByEmailUseCase
import ru.dreamteam.travelreminder.domen.use_cases.CheckFirstLaunchUseCase
import ru.dreamteam.travelreminder.domen.use_cases.DeleteTravelUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetTravelsUseCase
import ru.dreamteam.travelreminder.domen.use_cases.SignInByEmailAndPasswordUseCase
import ru.dreamteam.travelreminder.domen.use_cases.SignUpByEmailAndPasswordUseCase
import ru.dreamteam.travelreminder.presentation.MainActivityViewModel
import ru.dreamteam.travelreminder.presentation.change_password.ChangePasswordViewModel
import ru.dreamteam.travelreminder.presentation.show_map.MapViewModel
import ru.dreamteam.travelreminder.presentation.sing_in.SingInViewModel
import ru.dreamteam.travelreminder.presentation.sing_up.SignUpViewModel
import ru.dreamteam.travelreminder.presentation.travels_list.TravelsViewModel

val sharedModule = module {
    single { provideHttpClient(get(), get())}
    single { provideFirebaseApiKey() }

    single { AuthRepositoryImpl(get(), get()) }.bind<AuthRepository>()
    single { MapNavigationRepositoryImpl(get()) }.bind<MapNavigationRepository>()
    single { TravelLocalRepositoryImpl(get()) }.bind<TravelLocalRepository>()
    single { TravelRepositoryImpl(get(), get()) }.bind<TravelRepository>()

    single { CheckFirstLaunchUseCase(get()) }
    single { SignInByEmailAndPasswordUseCase(get()) }
    single { SignUpByEmailAndPasswordUseCase(get()) }
    single { ChangePasswordByEmailUseCase(get()) }
    single { DeleteTravelUseCase(get()) }
    single { AddTravelUseCase(get()) }
    single { GetTravelsUseCase(get()) }

    viewModel { MainActivityViewModel(get()) }
    viewModel { TravelsViewModel(get(), get(), get()) }
    viewModel { SingInViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { ChangePasswordViewModel(get()) }
    viewModel { MapViewModel(get(), get()) }
}