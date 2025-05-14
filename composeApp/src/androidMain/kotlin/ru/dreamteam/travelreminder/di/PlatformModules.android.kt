package ru.dreamteam.travelreminder.di

import org.koin.dsl.module
import ru.dreamteam.travelreminder.data.local.storage.UserUidStorage
import org.koin.android.ext.koin.androidContext
import ru.dreamteam.travelreminder.data.local.provider.AndroidLocaleProvider
import ru.dreamteam.travelreminder.data.local.provider.LocaleProvider


actual val platformModule = module {
    single {UserUidStorage(androidContext())}
    single<LocaleProvider> { AndroidLocaleProvider(get() ) }
}