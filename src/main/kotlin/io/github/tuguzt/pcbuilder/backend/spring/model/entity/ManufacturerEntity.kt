package io.github.tuguzt.pcbuilder.backend.spring.model.entity

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.Manufacturer
import org.springframework.data.util.ProxyUtils
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "manufacturer")
class ManufacturerEntity(
    @Id override val id: NanoId,
    override val name: String,
    override val description: String,
) : Manufacturer {
    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as ManufacturerEntity
        return this.id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
