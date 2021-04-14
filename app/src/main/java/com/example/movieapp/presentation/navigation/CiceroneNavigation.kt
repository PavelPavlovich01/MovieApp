package com.example.movieapp.presentation.navigation

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class CiceroneNavigation : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CiceroneNavigation)
            modules(myModule)
        }
    }

    private val myModule : Module = module {
        single { Cicerone.create(Router()) }
        single { get<Cicerone<Router>>().getNavigatorHolder() }
        single { get<Cicerone<Router>>().router}
    }
}