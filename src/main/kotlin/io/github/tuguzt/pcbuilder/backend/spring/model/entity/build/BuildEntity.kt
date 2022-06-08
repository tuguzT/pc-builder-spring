package io.github.tuguzt.pcbuilder.backend.spring.model.entity.build

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.cases.CaseEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.motherboard.MotherboardEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.UserEntity
import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import javax.persistence.*

@Entity
@Table(name = "build")
class BuildEntity(
    @Id override val id: String,
    val name: String,
    @ManyToOne(fetch = FetchType.EAGER) val user: UserEntity,
    @ManyToOne(fetch = FetchType.EAGER) val case: CaseEntity?,
    @ManyToOne(fetch = FetchType.EAGER) val motherboard: MotherboardEntity?,
) : Identifiable<String>
