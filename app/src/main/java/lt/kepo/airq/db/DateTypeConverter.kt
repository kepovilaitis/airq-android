package lt.kepo.airq.db

import androidx.room.TypeConverter
import java.util.*


class DateTypeConverter {
    @TypeConverter
    fun toDate(value: Long?): Date? = if (value == null) null else Date(value)

    @TypeConverter
    fun toLong(value: Date?): Long? = value?.time
}