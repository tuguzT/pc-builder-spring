package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.cases

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.ComponentEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.ManufacturerEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.SizeEmbeddable
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.WeightEmbeddable
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.cases.*
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor
import org.springframework.data.util.ProxyUtils
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "\"case\"")
class CaseEntity(
    id: NanoId,
    name: String,
    description: String,
    weight: WeightEmbeddable,
    size: SizeEmbeddable,
    manufacturer: ManufacturerEntity,
    type: CaseTypeEmbeddable,
    powerSupply: CasePowerSupplyEmbeddable?,
    override val powerSupplyShroud: CasePowerSupplyShroud,
    override val sidePanelWindow: CaseSidePanelWindow?,
    override val motherboardFormFactor: MotherboardFormFactor,
    driveBays: CaseDriveBaysEmbeddable,
    expansionSlots: CaseExpansionSlotsEmbeddable,
) : ComponentEntity(id, name, description, weight, size, manufacturer), Case {
    @Embedded
    private val typeEmbeddable = type

    @Embedded
    private val powerSupplyEmbeddable = powerSupply

    @Embedded
    private val driveBaysEmbeddable = driveBays

    @Embedded
    private val expansionSlotsEmbeddable = expansionSlots

    override val type: CaseType
        get() = typeEmbeddable.toCaseType()

    override val powerSupply: CasePowerSupply?
        get() = powerSupplyEmbeddable?.toPowerSupply()

    override val driveBays: CaseDriveBays
        get() = driveBaysEmbeddable.toCaseDriveBays()

    override val expansionSlots: CaseExpansionSlots
        get() = expansionSlotsEmbeddable.toExpansionSlots()

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as CaseEntity
        return this.id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
