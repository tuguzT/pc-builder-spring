package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.domain.model.component.Weight
import org.springframework.data.util.ProxyUtils
import javax.persistence.*

@Entity
@Table(name = "component")
@Inheritance(strategy = InheritanceType.JOINED)
open class ComponentEntity(
    @Id override val id: NanoId,
    override val name: String,
    override val description: String,
    weight: WeightEmbeddable,
    size: SizeEmbeddable,
    @ManyToOne @JoinColumn(name = "manufacturer_id")
    override val manufacturer: ManufacturerEntity,
) : Component {

    @Embedded
    private val sizeEmbeddable = size

    @Embedded
    private val weightEmbeddable = weight

    final override val weight: Weight
        get() = weightEmbeddable.toWeight()

    final override val size: Size
        get() = sizeEmbeddable.toSize()

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as ComponentEntity
        return this.id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
