package ru.dreamteam.travelreminder.di

import androidx.work.WorkManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.dreamteam.travelreminder.data.local.provider.LocaleProvider
import ru.dreamteam.travelreminder.data.local.storage.SecretApiKeys
import ru.dreamteam.travelreminder.data.local.storage.UserUidStorage
import ru.dreamteam.travelreminder.domen.repository.NotificationScheduler
import ru.dreamteam.travelreminder.sync.NetworkConnectivityObserver
import ru.dreamteam.travelreminder.sync.SyncController


actual val platformModule = module {
    single {UserUidStorage(androidContext())}
    single { NetworkConnectivityObserver(androidContext()) }
    single { SyncController(androidContext()) }
    single<String>(qualifier = named("firebaseApiKey")) {
        SecretApiKeys.getFirebaseApiKey()
    }
    single<String>(qualifier = named("googleApiServicesKey")) {
        SecretApiKeys.getGoogleApiServicesKey()
    }
    single { WorkManager.getInstance(androidContext()) }
    single { LocaleProvider(androidContext()) }
    single { NotificationScheduler(get()) }
}