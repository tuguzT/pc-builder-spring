package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component

import org.springframework.data.util.ProxyUtils
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "manufacturer")
class ManufacturerEntity(
    @Id val id: String,
    @Column(unique = true) val name: String,
    @Column(columnDefinition = "TEXT") val description: String,
) {
    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as ManufacturerEntity
        return this.id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
