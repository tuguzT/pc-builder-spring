package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.gpu

import io.github.tuguzt.pcbuilder.domain.model.component.gpu.GpuMultiSupport
import javax.persistence.Embeddable

@Embeddable
data class GpuMultiSupportEmbeddable(
    private val multiSupportName: String?,
    private val multiSupportCount: Int?,
) {
    fun toGpuMultiSupport(): GpuMultiSupport = when (multiSupportName) {
        "CrossFireX" -> GpuMultiSupport.CrossFireX(
            when (multiSupportCount) {
                2 -> GpuMultiSupport.CrossFireX.WayCount.TwoWay
                3 -> GpuMultiSupport.CrossFireX.WayCount.ThreeWay
                else -> GpuMultiSupport.CrossFireX.WayCount.FourWay
            }
        )
        else -> GpuMultiSupport.SLI(
            when (multiSupportCount) {
                2 -> GpuMultiSupport.SLI.WayCount.TwoWay
                3 -> GpuMultiSupport.SLI.WayCount.ThreeWay
                else -> GpuMultiSupport.SLI.WayCount.FourWay
            }
        )
    }
}

fun GpuMultiSupport.toEmbeddable(): GpuMultiSupportEmbeddable = when (this) {
    is GpuMultiSupport.CrossFireX -> GpuMultiSupportEmbeddable(
        multiSupportName = "CrossFireX",
        multiSupportCount = wayCount.count.toInt(),
    )
    is GpuMultiSupport.SLI -> GpuMultiSupportEmbeddable(
        multiSupportName = "SLI",
        multiSupportCount = wayCount.count.toInt(),
    )
}
