package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.UserEntity
import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.domain.model.component.Weight
import org.springframework.data.util.ProxyUtils
import javax.persistence.*

@Entity
@Table(name = "component")
@Inheritance(strategy = InheritanceType.JOINED)
open class ComponentEntity(
    @Id override val id: String,
    open val name: String,
    @Column(columnDefinition = "TEXT") open val description: String,
    weight: WeightEmbeddable,
    size: SizeEmbeddable,
    @ManyToOne @JoinColumn(name = "manufacturer_id")
    open val manufacturer: ManufacturerEntity,
    open val imageUri: String?,
    @ManyToMany(fetch = FetchType.EAGER)
    open val favorites: Set<UserEntity>,
) : Identifiable<String> {
    @Embedded
    private val sizeEmbeddable = size

    @Embedded
    private val weightEmbeddable = weight

    val weight: Weight
        get() = weightEmbeddable.toWeight()

    val size: Size
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
