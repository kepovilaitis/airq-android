package lt.kepo.airq.api.dto

import com.google.gson.annotations.SerializedName

data class AirQualityDto (
    @SerializedName("idx") val stationId: Int,
    @SerializedName("aqi") val airQualityIndex: Int,
    @SerializedName("dominentpol") val dominatingPollutant: String,
    @SerializedName("iaqi") val individualIndices: IndividualIndicesDto,
    @SerializedName("city") val city: CityDto
)