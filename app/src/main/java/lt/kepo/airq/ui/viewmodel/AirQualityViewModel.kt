package lt.kepo.airq.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.*
import lt.kepo.airq.data.api.ApiErrorResponse
import lt.kepo.airq.data.api.ApiSuccessResponse
import lt.kepo.airq.data.model.AirQuality
import lt.kepo.airq.data.repository.airquality.AirQualityRepository
import lt.kepo.airq.domain.UpdateAirQualitiesUseCase
import lt.kepo.airq.domain.UpdateAirQualityHereUseCase

class AirQualityViewModel(
    initAirQuality: AirQuality,
    application: Application,
    private val airQualityRepository: AirQualityRepository,
    locationClient: FusedLocationProviderClient,
    private val updateAirQualitiesUseCase: UpdateAirQualitiesUseCase,
    updateAirQualityHereUseCase: UpdateAirQualityHereUseCase
) : BaseAirQualityViewModel(application, locationClient, updateAirQualityHereUseCase) {
    val airQuality: LiveData<AirQuality> = airQualityRepository.getCachedAirQuality(initAirQuality.stationId)

    fun removeAirQuality() {
        viewModelScope.launch {
            airQuality.value?.let { airQualityRepository.deleteCachedAirQuality(it.stationId) }
        }
    }

    fun updateAirQuality(force: Boolean = false) {
        if (airQuality.value?.isCurrentLocationQuality == true)
            updateLocalAirQualityHere(force, airQuality.value)
        else {
            viewModelScope.launch {
                _isLoading.value = true

                airQuality.value?.let {
                    when (val result = updateAirQualitiesUseCase(force, listOf(it))) {
                        is ApiSuccessResponse -> _errorMessage.value = null
                        is ApiErrorResponse<*> -> _errorMessage.value = result.error
                    }
                }

                _isLoading.value = false
            }
        }
    }
}