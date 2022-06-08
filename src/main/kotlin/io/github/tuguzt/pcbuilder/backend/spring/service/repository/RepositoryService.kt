package io.github.tuguzt.pcbuilder.backend.spring.service.repository

import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import org.springframework.data.domain.Pageable

sealed interface RepositoryService<I, T : Identifiable<I>> {
    suspend fun getAll(): List<T>

    suspend fun save(item: T): T

    suspend fun delete(item: T)

    suspend fun findById(id: I): T?

    suspend fun deleteById(id: I)

    suspend fun exists(item: T): Boolean

    suspend fun getAll(pageable: Pageable): List<T>
}
