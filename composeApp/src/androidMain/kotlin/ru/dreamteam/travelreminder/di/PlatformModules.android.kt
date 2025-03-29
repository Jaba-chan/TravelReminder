package ru.dreamteam.travelreminder.di

import org.koin.dsl.module
import ru.dreamteam.travelreminder.data.local.storage.UserUidStorage
import org.koin.android.ext.koin.androidContext



actual val platformModule = module {
    single {UserUidStorage(androidContext())}
}