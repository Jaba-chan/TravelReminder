package ru.dreamteam.travelreminder.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import ru.dreamteam.travelreminder.data.local.room_db.platformTravelDatabaseModule

fun initKoin(config: KoinAppDeclaration? = null){
    startKoin{
        config?.invoke(this)
        modules(sharedModule, platformModule, platformTravelDatabaseModule("travels.db"))
        printLogger()
    }
}