package lt.kepo.stations

import kotlinx.coroutines.flow.*
import lt.kepo.airqualitydatabase.AirQualityDao
import javax.inject.Inject

class RemoteStationsRepository @Inject constructor(
    private val getStationsUseCase: GetStationsUseCase,
    private val addStationUseCase: AddStationUseCase,
    private val airQualityDao: AirQualityDao,
) : StationsRepository {

    private val savedAirQualities = airQualityDao.getAll()
    private val _stations = MutableStateFlow<List<Station>>(emptyList())
    override val stations: Flow<List<Station>> = _stations
        .combine(savedAirQualities) { newStations, savedAirQualities ->
            newStations
        }

    override suspend fun save(stationId: Int): StationsRepository.SaveResult =
        when (addStationUseCase(stationId)) {
            is AddStationUseCase.Result.Success -> {
                _stations.value = _stations.value.filterNot { it.id == stationId }
                StationsRepository.SaveResult.Success
            }
            is AddStationUseCase.Result.Error -> {
                StationsRepository.SaveResult.Error
            }
        }

    override suspend fun search(query: String): StationsRepository.SearchResult =
        when (val result = getStationsUseCase(query)) {
            is GetStationsUseCase.Result.Success -> {
                val filteredResult = result.stations.filterNot { station ->
                    savedAirQualities.firstOrNull()
                        ?.map { it.stationId }
                        ?.contains(station.id)
                        ?: false
                }
                _stations.emit(filteredResult)
                if (filteredResult.isNotEmpty()) {
                    StationsRepository.SearchResult.Success
                } else {
                    StationsRepository.SearchResult.NothingFound
                }
            }
            is GetStationsUseCase.Result.Error -> {
                StationsRepository.SearchResult.Error
            }
        }

    override suspend fun clear() {
        _stations.emit(emptyList())
    }
}