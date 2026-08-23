package sikxx.toys.sikxxplaylink.di

import androidx.room.Room
import sikxx.toys.sikxxplaylink.data.database.TNQRSDatabase
import org.koin.dsl.module

private const val DB_NAME = "tnqrs_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = TNQRSDatabase::class.java,
            name = DB_NAME
        ).build()
    }

    single { get<TNQRSDatabase>().cartItemDao() }

    single { get<TNQRSDatabase>().orderDao() }
}