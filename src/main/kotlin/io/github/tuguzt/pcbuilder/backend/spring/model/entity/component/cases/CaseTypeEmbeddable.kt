package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.cases

import io.github.tuguzt.pcbuilder.domain.model.component.cases.CaseType
import javax.persistence.Embeddable

@Embeddable
data class CaseTypeEmbeddable(private val name: String) {
    fun toCaseType(): CaseType = when (name) {
        "ATX_Desktop" -> CaseType.ATX.Desktop
        "ATX_FullTower" -> CaseType.ATX.FullTower
        "ATX_MidTower" -> CaseType.ATX.MidTower
        "ATX_MiniTower" -> CaseType.ATX.MiniTower
        "ATX_TestBench" -> CaseType.ATX.TestBench
        "MicroATX_Desktop" -> CaseType.MicroATX.Desktop
        "MicroATX_MidTower" -> CaseType.MicroATX.MidTower
        "MicroATX_MiniTower" -> CaseType.MicroATX.MiniTower
        "MicroATX_Slim" -> CaseType.MicroATX.Slim
        "MiniITX_Desktop" -> CaseType.MiniITX.Desktop
        "MiniITX_TestBench" -> CaseType.MiniITX.TestBench
        "MiniITX_Tower" -> CaseType.MiniITX.Tower
        "HTPC" -> CaseType.HTPC
        else -> throw IllegalStateException()
    }
}

fun CaseType.toEmbeddable(): CaseTypeEmbeddable {
    val name = when (this) {
        CaseType.ATX.Desktop -> "ATX_Desktop"
        CaseType.ATX.FullTower -> "ATX_FullTower"
        CaseType.ATX.MidTower -> "ATX_MidTower"
        CaseType.ATX.MiniTower -> "ATX_MiniTower"
        CaseType.ATX.TestBench -> "ATX_TestBench"
        CaseType.MicroATX.Desktop -> "MicroATX_Desktop"
        CaseType.MicroATX.MidTower -> "MicroATX_MidTower"
        CaseType.MicroATX.MiniTower -> "MicroATX_MiniTower"
        CaseType.MicroATX.Slim -> "MicroATX_Slim"
        CaseType.MiniITX.Desktop -> "MiniITX_Desktop"
        CaseType.MiniITX.TestBench -> "MiniITX_TestBench"
        CaseType.MiniITX.Tower -> "MiniITX_Tower"
        CaseType.HTPC -> "HTPC"
    }
    return CaseTypeEmbeddable(name)
}
