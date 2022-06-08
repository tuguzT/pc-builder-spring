package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.cases

import io.github.tuguzt.pcbuilder.domain.model.component.cases.CaseType
import javax.persistence.Embeddable

@Embeddable
data class CaseTypeEmbeddable(private val name: String) {
    fun toCaseType(): CaseType = name.toCaseType()
}

fun CaseType.toEmbeddable(): CaseTypeEmbeddable = CaseTypeEmbeddable(name = "$this")

fun String.toCaseType(): CaseType =
    CaseType.ATX.values().firstOrNull { "$it" == this }
        ?: CaseType.MicroATX.values().firstOrNull { "$it" == this }
        ?: CaseType.MiniITX.values().firstOrNull { "$it" == this }
        ?: CaseType.HTPC.takeIf { "$it" == this }
        ?: throw IllegalStateException()
