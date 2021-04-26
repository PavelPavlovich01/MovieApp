package com.example.movieapp.presentation.navigation

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.core.module.Module
import org.koin.dsl.module

 val ciceroneModule : Module = module {
     single { Cicerone.create(Router()) }
     single { get<Cicerone<Router>>().getNavigatorHolder() }
     single { LocalCiceroneHolder() }
     single { get<Cicerone<Router>>().router}
 }
