package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.motherboard

import io.github.tuguzt.pcbuilder.domain.model.component.cpu.CpuSocket
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardCpuSocket
import javax.persistence.Embeddable

@Embeddable
data class MotherboardCpuSocketEmbeddable(private val cpuSocketName: String) {
    fun toMotherboardCpuSocket(): MotherboardCpuSocket =
        CpuSocket.values().firstOrNull { "$it" == cpuSocketName }?.let { MotherboardCpuSocket.Standard(it) }
            ?: MotherboardCpuSocket.Integrated.values().first { "$it" == cpuSocketName }
}

fun MotherboardCpuSocket.toEmbeddable(): MotherboardCpuSocketEmbeddable = when (this) {
    is MotherboardCpuSocket.Standard -> MotherboardCpuSocketEmbeddable(socket.toString())
    is MotherboardCpuSocket.Integrated -> MotherboardCpuSocketEmbeddable(toString())
}
