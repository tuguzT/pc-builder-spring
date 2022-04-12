package io.github.tuguzt.pcbuilder.backend.spring.service.impl

import io.github.tuguzt.pcbuilder.backend.spring.model.UserEntity
import io.github.tuguzt.pcbuilder.backend.spring.repository.UserRepository
import io.github.tuguzt.pcbuilder.backend.spring.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val repository: UserRepository) : UserService {
    override suspend fun getAll(): List<UserEntity> = withContext(Dispatchers.IO) { repository.findAll() }

    override suspend fun save(entity: UserEntity) = withContext(Dispatchers.IO) { repository.save(entity) }

    override suspend fun delete(entity: UserEntity) = withContext(Dispatchers.IO) { repository.delete(entity) }

    override suspend fun findById(id: String) = withContext(Dispatchers.IO) { repository.findByIdOrNull(id) }

    override suspend fun deleteById(id: String) = withContext(Dispatchers.IO) { repository.deleteById(id) }

    override suspend fun exists(entity: UserEntity) = withContext(Dispatchers.IO) { repository.existsById(entity.id) }

    override suspend fun findByEmail(email: String) = withContext(Dispatchers.IO) { repository.findByEmail(email) }
}
