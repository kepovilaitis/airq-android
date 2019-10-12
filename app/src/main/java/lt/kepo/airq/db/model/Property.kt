package lt.kepo.airq.db.model

import androidx.annotation.Nullable
import lt.kepo.airq.api.dto.PropertyDto

data class Property (
    val value: Double
) {
    companion object {
        fun build(@Nullable dto: PropertyDto?): Property {
            return when (dto) {
                null -> Property(-1.0)
                else -> Property(dto.value)
            }
        }
    }
}