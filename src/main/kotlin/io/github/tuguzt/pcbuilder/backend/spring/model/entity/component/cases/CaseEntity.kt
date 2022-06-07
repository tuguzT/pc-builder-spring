package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.cases

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.ComponentEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.ManufacturerEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.SizeEmbeddable
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.WeightEmbeddable
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.motherboard.MotherboardFormFactorEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.UserEntity
import io.github.tuguzt.pcbuilder.domain.model.component.cases.*
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor
import org.springframework.data.util.ProxyUtils
import javax.persistence.*

@Entity
@Table(name = "\"case\"")
class CaseEntity(
    id: String,
    name: String,
    description: String,
    weight: WeightEmbeddable,
    size: SizeEmbeddable,
    manufacturer: ManufacturerEntity,
    imageUri: String?,
    favorites: MutableSet<UserEntity>,
    type: CaseTypeEmbeddable,
    powerSupply: CasePowerSupplyEmbeddable?,
    val powerSupplyShroud: CasePowerSupplyShroud,
    val sidePanelWindow: CaseSidePanelWindow?,
    @ManyToMany(fetch = FetchType.EAGER)
    private val motherboardFormFactorEntities: List<MotherboardFormFactorEntity>,
    driveBays: CaseDriveBaysEmbeddable,
    expansionSlots: CaseExpansionSlotsEmbeddable,
) : ComponentEntity(id, name, description, weight, size, manufacturer, imageUri, favorites) {
    @Embedded
    private val typeEmbeddable = type

    @Embedded
    private val powerSupplyEmbeddable = powerSupply

    @Embedded
    private val driveBaysEmbeddable = driveBays

    @Embedded
    private val expansionSlotsEmbeddable = expansionSlots

    val caseType: CaseType
        get() = typeEmbeddable.toCaseType()

    val powerSupply: CasePowerSupply?
        get() = powerSupplyEmbeddable?.toPowerSupply()

    val driveBays: CaseDriveBays
        get() = driveBaysEmbeddable.toCaseDriveBays()

    val expansionSlots: CaseExpansionSlots
        get() = expansionSlotsEmbeddable.toExpansionSlots()

    val motherboardFormFactors: List<MotherboardFormFactor>
        get() = motherboardFormFactorEntities.map { it.id }

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as CaseEntity
        return this.id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
