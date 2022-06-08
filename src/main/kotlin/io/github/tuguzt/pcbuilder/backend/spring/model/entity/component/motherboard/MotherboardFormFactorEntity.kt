package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.motherboard

import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor
import org.springframework.data.util.ProxyUtils
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "motherboard_form_factor")
data class MotherboardFormFactorEntity(@Id override val id: MotherboardFormFactor) :
    Identifiable<MotherboardFormFactor> {

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as MotherboardFormFactorEntity
        return this.id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
