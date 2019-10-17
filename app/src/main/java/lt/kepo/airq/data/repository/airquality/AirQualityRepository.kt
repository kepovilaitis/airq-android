package lt.kepo.airq.data.repository.airquality

import android.location.Location
import androidx.lifecycle.LiveData
import lt.kepo.airq.data.api.ApiResponse
import lt.kepo.airq.db.model.AirQuality

interface AirQualityRepository {
    suspend fun getRemoteAirQualityHere(location: Location?): ApiResponse<AirQuality>

    suspend fun getRemoteAirQuality(stationId: Int): AirQuality

    suspend fun getLocalAirQualityHere(): AirQuality

    suspend fun getLocalAirQuality(stationId: Int): AirQuality

    suspend fun upsertLocalAirQualityHere(airQuality: AirQuality)
}