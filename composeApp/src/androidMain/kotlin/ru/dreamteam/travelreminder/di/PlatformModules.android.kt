package ru.dreamteam.travelreminder.di

import org.koin.dsl.module
import ru.dreamteam.travelreminder.data.local.storage.UserUidStorage
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import ru.dreamteam.travelreminder.data.local.provider.AndroidLocaleProvider
import ru.dreamteam.travelreminder.data.local.provider.LocaleProvider
import ru.dreamteam.travelreminder.data.local.storage.SecretApiKeys
import ru.dreamteam.travelreminder.domen.repository.NetworkConnectivityObserver


actual val platformModule = module {
    single {UserUidStorage(androidContext())}
    single<LocaleProvider> { AndroidLocaleProvider(get() ) }
    single { NetworkConnectivityObserver(androidContext()) }
    single<String>(qualifier = named("firebaseApiKey")) {
        SecretApiKeys.getFirebaseApiKey()
    }
    single<String>(qualifier = named("googleApiServicesKey")) {
        SecretApiKeys.getGoogleApiServicesKey()
    }
}