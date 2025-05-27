package ru.dreamteam.travelreminder.di


import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dreamteam.travelreminder.common.ErrorMapper
import ru.dreamteam.travelreminder.data.local.dao.SyncActionDao
import ru.dreamteam.travelreminder.data.repository.DefaultTravelRepository
import ru.dreamteam.travelreminder.data.local.dao.TravelsDao
import ru.dreamteam.travelreminder.data.local.repository.LocalTravelRepositoryImpl
import ru.dreamteam.travelreminder.data.local.repository.RoomSyncQueue
import ru.dreamteam.travelreminder.data.local.room_db.SyncActionsDatabase
import ru.dreamteam.travelreminder.data.local.room_db.TravelsDatabase
import ru.dreamteam.travelreminder.data.local.storage.provideFirebaseApiKey
import ru.dreamteam.travelreminder.data.remoute.provideHttpClient
import ru.dreamteam.travelreminder.data.remoute.repository.AuthRepositoryImpl
import ru.dreamteam.travelreminder.data.remoute.repository.MapRepositoryImpl
import ru.dreamteam.travelreminder.data.remoute.repository.RemoteTravelRepositoryImpl
import ru.dreamteam.travelreminder.data.repository.NotifyingTravelRepository
import ru.dreamteam.travelreminder.domen.repository.AuthRepository
import ru.dreamteam.travelreminder.domen.repository.LocalTravelRepository
import ru.dreamteam.travelreminder.domen.repository.MapRepository
import ru.dreamteam.travelreminder.domen.repository.NotificationScheduler
import ru.dreamteam.travelreminder.domen.repository.RemoteTravelRepository
import ru.dreamteam.travelreminder.domen.repository.SyncQueue
import ru.dreamteam.travelreminder.domen.repository.TravelRepository
import ru.dreamteam.travelreminder.domen.use_cases.AddTravelUseCase
import ru.dreamteam.travelreminder.domen.use_cases.ChangePasswordByEmailUseCase
import ru.dreamteam.travelreminder.domen.use_cases.CheckFirstLaunchUseCase
import ru.dreamteam.travelreminder.domen.use_cases.DeleteTravelUseCase
import ru.dreamteam.travelreminder.domen.use_cases.EditTravelUseCase
import ru.dreamteam.travelreminder.domen.use_cases.FillTableUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetNavigationRouteUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetNearbyPlacesUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetPlaceCoordinatesUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetPlaceSuggestionUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetTravelByIdUseCase
import ru.dreamteam.travelreminder.domen.use_cases.GetTravelsUseCase
import ru.dreamteam.travelreminder.domen.use_cases.LogOutUseCase
import ru.dreamteam.travelreminder.domen.use_cases.SignInByEmailAndPasswordUseCase
import ru.dreamteam.travelreminder.domen.use_cases.SignUpByEmailAndPasswordUseCase
import ru.dreamteam.travelreminder.presentation.DefaultErrorMapper
import ru.dreamteam.travelreminder.presentation.MainActivityViewModel
import ru.dreamteam.travelreminder.presentation.add_travel.AddTravelViewModel
import ru.dreamteam.travelreminder.presentation.change_password.ChangePasswordViewModel
import ru.dreamteam.travelreminder.presentation.sing_in.SingInViewModel
import ru.dreamteam.travelreminder.presentation.sing_up.SignUpViewModel
import ru.dreamteam.travelreminder.presentation.travels_list.TravelsViewModel
import ru.dreamteam.travelreminder.sync.SyncManager

val sharedModule = module {
    single { provideHttpClient(get(), get()) }
    single { provideFirebaseApiKey() }
    single { DefaultErrorMapper() }.bind<ErrorMapper>()
    single { SyncManager(get(), get()) }

    single<TravelsDao> {
        get<TravelsDatabase>().travelsDao()
    }
    single<SyncActionDao> {
        get<SyncActionsDatabase>().syncDao()
    }
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

    single<TravelRepository> {
        NotifyingTravelRepository(
            delegate  = DefaultTravelRepository(
                localRepository       = get(),
                remoteTravelRepository= get(),
                syncQueue             = get(),
                networkObserver       = get()
            ),
            scheduler = get<NotificationScheduler>()
        )
    }
    single { LocalTravelRepositoryImpl(get()) }.bind<LocalTravelRepository>()
    single { RemoteTravelRepositoryImpl(get(), get()) }.bind<RemoteTravelRepository>()
    single { RoomSyncQueue(get()) }.bind<SyncQueue>()


    single { LogOutUseCase(get(), get()) }
    single { FillTableUseCase(get()) }
    single { CheckFirstLaunchUseCase(get()) }
    single { SignInByEmailAndPasswordUseCase(get(), get()) }
    single { SignUpByEmailAndPasswordUseCase(get(), get()) }
    single { ChangePasswordByEmailUseCase(get(), get()) }

    single { DeleteTravelUseCase(get(), get()) }
    single { AddTravelUseCase(get(), get()) }
    single { GetTravelsUseCase(get(), get()) }
    single { GetTravelByIdUseCase(get(), get()) }
    single { EditTravelUseCase(get(), get()) }
    single { EditTravelUseCase(get(), get()) }

    single { GetPlaceSuggestionUseCase(get(), get()) }
    single { GetNearbyPlacesUseCase(get(), get()) }
    single { GetPlaceCoordinatesUseCase(get(), get()) }
    single { GetNavigationRouteUseCase(get(), get()) }


    viewModel { MainActivityViewModel(get()) }
    viewModel { TravelsViewModel(get(), get(), get(), get(), get()) }
    viewModel { SingInViewModel(get(), get(), get()) }
    viewModel { SignUpViewModel(get(), get()) }
    viewModel { ChangePasswordViewModel(get()) }
    viewModel {
        AddTravelViewModel(
            get(), get(), get(),
            get(), get(), get(),
            get(), get(), get()
        )
    }
}