package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.domain.model.Identifiable

sealed interface RepositoryService<T : Identifiable<I>, I : Any> {
    suspend fun getAll(): List<T>

    suspend fun save(item: T): T

    suspend fun delete(item: T)

    suspend fun findById(id: I): T?

    suspend fun deleteById(id: I)

    suspend fun exists(item: T): Boolean
}
