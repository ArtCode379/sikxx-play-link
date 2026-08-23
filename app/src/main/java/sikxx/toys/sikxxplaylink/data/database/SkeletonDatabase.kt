package sikxx.toys.sikxxplaylink.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import sikxx.toys.sikxxplaylink.data.dao.CartItemDao
import sikxx.toys.sikxxplaylink.data.dao.OrderDao
import sikxx.toys.sikxxplaylink.data.database.converter.Converters
import sikxx.toys.sikxxplaylink.data.entity.CartItemEntity
import sikxx.toys.sikxxplaylink.data.entity.OrderEntity

@Database(
    entities = [CartItemEntity::class, OrderEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TNQRSDatabase : RoomDatabase() {

    abstract fun cartItemDao(): CartItemDao

    abstract fun orderDao(): OrderDao
}