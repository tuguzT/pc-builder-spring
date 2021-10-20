package io.github.tuguzt.pcbuilder.backend.spring.repository

import kotlinx.coroutines.flow.Flow

/**
 * Repository type with basic CRUD (Create, Read, Update, Delete) operations.
 */
interface CRUDRepository<T : Any> {
    /**
     * Inserts the entity of type [T] into repository.
     */
    suspend fun insert(item: T)

    /**
     * Returns all entities of type [T] contained in repository.
     */
    suspend fun getAll(): Flow<T>

    /**
     * Updates the entity of type [T] with its values.
     */
    suspend fun update(item: T)

    /**
     * Deletes the entity of type [T] from the repository.
     */
    suspend fun delete(item: T)
}
