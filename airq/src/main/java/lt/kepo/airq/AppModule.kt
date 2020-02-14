package lt.kepo.airq

import lt.kepo.core.ui.AppNavigator
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule : Module = module {
    factory<AppNavigator> { AirQualitiesNavigatorImpl() }
}