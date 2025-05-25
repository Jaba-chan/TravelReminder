package ru.dreamteam.travelreminder.di


import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dreamteam.travelreminder.common.ErrorMapper
import ru.dreamteam.travelreminder.data.local.repository.TravelLocalRepositoryImpl
import ru.dreamteam.travelreminder.data.local.storage.provideFirebaseApiKey
import ru.dreamteam.travelreminder.data.remoute.provideHttpClient
import ru.dreamteam.travelreminder.data.remoute.repository.AuthRepositoryImpl
import ru.dreamteam.travelreminder.data.remoute.repository.MapRepositoryImpl
import ru.dreamteam.travelreminder.data.remoute.repository.TravelRepositoryImpl
import ru.dreamteam.travelreminder.domen.repository.AuthRepository
import ru.dreamteam.travelreminder.domen.repository.MapRepository
import ru.dreamteam.travelreminder.domen.repository.TravelLocalRepository
import ru.dreamteam.travelreminder.domen.repository.TravelRepository
import ru.dreamteam.travelreminder.domen.use_cases.AddTravelUseCase
import ru.dreamteam.travelreminder.domen.use_cases.ChangePasswordByEmailUseCase
import ru.dreamteam.travelreminder.domen.use_cases.CheckFirstLaunchUseCase
import ru.dreamteam.travelreminder.domen.use_cases.DeleteTravelUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetNavigationRouteUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetNearbyPlacesUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetPlaceCoordinatesUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetPlaceSuggestionUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetTravelsUseCase
import ru.dreamteam.travelreminder.domen.use_cases.SignInByEmailAndPasswordUseCase
import ru.dreamteam.travelreminder.domen.use_cases.SignUpByEmailAndPasswordUseCase
import ru.dreamteam.travelreminder.presentation.DefaultErrorMapper
import ru.dreamteam.travelreminder.presentation.MainActivityViewModel
import ru.dreamteam.travelreminder.presentation.add_travel.AddTravelViewModel
import ru.dreamteam.travelreminder.presentation.change_password.ChangePasswordViewModel
import ru.dreamteam.travelreminder.presentation.show_map.MapViewModel
import ru.dreamteam.travelreminder.presentation.show_map.SharedPlaceFlow
import ru.dreamteam.travelreminder.presentation.sing_in.SingInViewModel
import ru.dreamteam.travelreminder.presentation.sing_up.SignUpViewModel
import ru.dreamteam.travelreminder.presentation.travels_list.TravelsViewModel

val sharedModule = module {
    single { provideHttpClient(get(), get()) }
    single { provideFirebaseApiKey() }
    single { DefaultErrorMapper() }.bind<ErrorMapper>()

    single {
        AuthRepositoryImpl(
            get(),
            get(),
            apiKey = get(named("firebaseApiKey"))
        )
    }.bind<AuthRepository>()
    single {
        MapRepositoryImpl(
            get(),
            apiKey = get(named("googleApiServicesKey"))
        )
    }.bind<MapRepository>()

    single { TravelLocalRepositoryImpl(get()) }.bind<TravelLocalRepository>()
    single { TravelRepositoryImpl(get(), get()) }.bind<TravelRepository>()

    single { CheckFirstLaunchUseCase(get()) }
    single { SignInByEmailAndPasswordUseCase(get(), get()) }
    single { SignUpByEmailAndPasswordUseCase(get(), get()) }
    single { ChangePasswordByEmailUseCase(get(), get()) }
    single { DeleteTravelUseCase(get(), get()) }
    single { AddTravelUseCase(get(), get()) }
    single { GetTravelsUseCase(get(), get()) }
    single { GetPlaceSuggestionUseCase(get(), get()) }
    single { GetNearbyPlacesUseCase(get(), get()) }
    single { GetPlaceCoordinatesUseCase(get(), get()) }
    single { GetNavigationRouteUseCase(get(), get()) }
    single { SharedPlaceFlow() }

    viewModel { MainActivityViewModel(get()) }
    viewModel { TravelsViewModel(get(), get()) }
    viewModel { SingInViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { ChangePasswordViewModel(get()) }
    viewModel { AddTravelViewModel(get(), get(), get()) }
    viewModel { MapViewModel(get(), get(), get(), get(), get(), get()) }
}