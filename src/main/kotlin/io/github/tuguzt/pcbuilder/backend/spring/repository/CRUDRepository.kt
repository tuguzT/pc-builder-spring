package io.github.tuguzt.pcbuilder.backend.spring.repository

interface CRUDRepository<T : Any> {
    fun insert(item: T)

    fun getAll(): List<T>

    fun update(item: T)

    fun delete(item: T)
}
