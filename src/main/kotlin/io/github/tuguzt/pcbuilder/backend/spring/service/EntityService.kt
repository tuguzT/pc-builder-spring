package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.domain.model.Identifiable

sealed interface EntityService<T : Identifiable<I>, I : Any> {
    suspend fun getAll(): List<T>

    suspend fun save(entity: T): T

    suspend fun delete(entity: T)

    suspend fun findById(id: I): T?

    suspend fun deleteById(id: I)

    suspend fun exists(entity: T): Boolean
}
