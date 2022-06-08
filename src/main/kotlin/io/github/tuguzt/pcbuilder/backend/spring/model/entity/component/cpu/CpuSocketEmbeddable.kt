package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.cpu

import io.github.tuguzt.pcbuilder.domain.model.component.cpu.CpuSocket
import javax.persistence.Embeddable

@Embeddable
data class CpuSocketEmbeddable(private val socketName: String) {
    fun toCpuSocket(): CpuSocket = CpuSocket.values().first { "$it" == socketName }
}

fun CpuSocket.toEmbeddable(): CpuSocketEmbeddable = CpuSocketEmbeddable(toString())
