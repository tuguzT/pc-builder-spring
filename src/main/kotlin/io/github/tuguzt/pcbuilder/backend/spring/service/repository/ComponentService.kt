package io.github.tuguzt.pcbuilder.backend.spring.service.repository

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import org.springframework.data.domain.Pageable

/**
 * Basic interface for service of [PolymorphicComponent].
 */
interface ComponentService {
    suspend fun getAll(currentUser: UserData): List<PolymorphicComponent>

    suspend fun save(item: PolymorphicComponent, currentUser: UserData): PolymorphicComponent

    suspend fun delete(item: PolymorphicComponent)

    suspend fun findById(id: NanoId, currentUser: UserData): PolymorphicComponent?

    suspend fun deleteById(id: NanoId)

    suspend fun exists(item: PolymorphicComponent): Boolean

    suspend fun getAll(pageable: Pageable, currentUser: UserData): List<PolymorphicComponent>

    suspend fun findByName(name: String, currentUser: UserData): PolymorphicComponent?
}
