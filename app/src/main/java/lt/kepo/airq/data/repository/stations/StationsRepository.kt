package lt.kepo.airq.data.repository.stations

import lt.kepo.airq.data.api.ApiResponse
import lt.kepo.airq.db.model.AirQuality
import lt.kepo.airq.db.model.Station

interface StationsRepository {
    suspend fun getRemoteStations(query: String): ApiResponse<List<Station>>

    suspend fun getLocalAllStations(): List<Station>

    suspend fun getRemoteStation(stationId: Int): ApiResponse<AirQuality>

    suspend fun insertLocalStation(station: Station)

    suspend fun deleteLocalStation(station: Station)
}