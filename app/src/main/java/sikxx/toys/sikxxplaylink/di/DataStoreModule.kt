package sikxx.toys.sikxxplaylink.di

import sikxx.toys.sikxxplaylink.data.datastore.TNQRSOnboardingPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { TNQRSOnboardingPrefs(androidContext()) }
}