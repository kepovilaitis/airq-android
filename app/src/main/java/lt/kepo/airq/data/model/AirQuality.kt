package lt.kepo.airq.db.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "air_qualities")
data class AirQuality (
    @SerializedName("idx") @PrimaryKey @ColumnInfo(name = "station_id") val stationId: Int,
    @SerializedName("aqi") @ColumnInfo(name = "air_quality_index") val airQualityIndex: String,
    @SerializedName("dominentpol") @ColumnInfo(name = "dominating_pollutant") val dominatingPollutant: String,
    @SerializedName("iaqi") @Embedded val individualIndices: IndividualIndices,
    @SerializedName("city") @Embedded(prefix = "city_") val city: City,
    @SerializedName("time") @Embedded(prefix = "time_") val time: Time,
    @Expose(deserialize = false, serialize = false) @ColumnInfo(name = "is_current_location_quality") var isCurrentLocationQuality: Boolean
)