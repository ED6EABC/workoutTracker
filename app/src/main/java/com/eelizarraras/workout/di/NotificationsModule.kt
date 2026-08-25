package com.eelizarraras.workout.di

import com.eelizarraras.workout.core.domine.notifications.CustomNotificationsManager
import org.koin.dsl.module

val notificationModules = module {
     single{ CustomNotificationsManager(get()) }
}