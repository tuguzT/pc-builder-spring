package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.motherboard

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.ComponentEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.ManufacturerEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.SizeEmbeddable
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.WeightEmbeddable
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.gpu.GpuMultiSupportEmbeddable
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.UserEntity
import io.github.tuguzt.pcbuilder.domain.model.component.gpu.GpuMultiSupport
import io.github.tuguzt.pcbuilder.domain.model.component.memory.MemoryECCType
import io.github.tuguzt.pcbuilder.domain.model.component.memory.MemoryType
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.*
import org.springframework.data.util.ProxyUtils
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "motherboard")
class MotherboardEntity(
    id: String,
    name: String,
    description: String,
    weight: WeightEmbeddable,
    size: SizeEmbeddable,
    manufacturer: ManufacturerEntity,
    imageUri: String?,
    favorites: Set<UserEntity>,
    chipset: MotherboardChipsetEmbeddable,
    cpuSocket: MotherboardCpuSocketEmbeddable,
    @ManyToOne val formFactorEntity: MotherboardFormFactorEntity,
    memoryAmount: MotherboardMemoryAmountEmbeddable,
    val memoryECCType: MemoryECCType,
    memorySlotCount: MotherboardMemorySlotCountEmbeddable,
    val memoryType: MemoryType,
    multiGpuSupport: GpuMultiSupportEmbeddable?,
    ports: MotherboardPortsEmbeddable,
    slots: MotherboardSlotsEmbeddable,
    usbHeaders: MotherboardUsbHeadersEmbeddable,
) : ComponentEntity(id, name, description, weight, size, manufacturer, imageUri, favorites) {

    @Embedded
    private val chipsetEmbeddable = chipset

    val chipset: MotherboardChipset
        get() = chipsetEmbeddable.toMotherboardChipset()

    @Embedded
    private val cpuSocketEmbeddable = cpuSocket

    val cpuSocket: MotherboardCpuSocket
        get() = cpuSocketEmbeddable.toMotherboardCpuSocket()

    @Embedded
    private val memoryAmountEmbeddable = memoryAmount

    val memoryAmount: MotherboardMemoryAmount
        get() = memoryAmountEmbeddable.toMotherboardMemoryAmount()

    @Embedded
    private val memorySlotCountEmbeddable = memorySlotCount

    val memorySlotCount: MotherboardMemorySlotCount
        get() = memorySlotCountEmbeddable.toMotherboardMemorySlotCount()

    @Embedded
    private val multiGpuSupportEmbeddable = multiGpuSupport

    val multiGpuSupport: GpuMultiSupport?
        get() = multiGpuSupportEmbeddable?.toGpuMultiSupport()

    @Embedded
    private val portsEmbeddable = ports

    val ports: MotherboardPorts
        get() = portsEmbeddable.toMotherboardPorts()

    @Embedded
    private val slotsEmbeddable = slots

    val slots: MotherboardSlots
        get() = slotsEmbeddable.toMotherboardSlots()

    private val usbHeadersEmbeddable = usbHeaders

    val usbHeaders: MotherboardUsbHeaders
        get() = usbHeadersEmbeddable.toMotherboardUsbHeaders()

    val formFactor: MotherboardFormFactor
        get() = formFactorEntity.id

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as MotherboardEntity
        return this.id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
