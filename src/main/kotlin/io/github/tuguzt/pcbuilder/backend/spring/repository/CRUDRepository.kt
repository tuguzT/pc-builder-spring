package io.github.tuguzt.pcbuilder.backend.spring.repository

import kotlinx.coroutines.flow.Flow

interface CRUDRepository<T : Any> {
    suspend fun insert(item: T)

    suspend fun getAll(): Flow<T>

    suspend fun update(item: T)

    suspend fun delete(item: T)
}
