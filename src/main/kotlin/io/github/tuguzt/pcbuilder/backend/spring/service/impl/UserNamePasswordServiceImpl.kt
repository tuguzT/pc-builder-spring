package io.github.tuguzt.pcbuilder.backend.spring.service.impl

import io.github.tuguzt.pcbuilder.backend.spring.model.UserNamePasswordEntity
import io.github.tuguzt.pcbuilder.backend.spring.repository.UserNamePasswordRepository
import io.github.tuguzt.pcbuilder.backend.spring.repository.UserRepository
import io.github.tuguzt.pcbuilder.backend.spring.service.UserNamePasswordService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserNamePasswordServiceImpl(
    private val repository: UserNamePasswordRepository,
    private val parentRepository: UserRepository,
) : UserNamePasswordService {

    override suspend fun save(entity: UserNamePasswordEntity) = withContext(Dispatchers.IO) { repository.save(entity) }

    override suspend fun delete(entity: UserNamePasswordEntity) =
        withContext(Dispatchers.IO) { repository.delete(entity) }

    override suspend fun getAll(): List<UserNamePasswordEntity> = withContext(Dispatchers.IO) { repository.findAll() }

    override suspend fun findById(id: String) = withContext(Dispatchers.IO) { repository.findByIdOrNull(id) }

    override suspend fun deleteById(id: String) = withContext(Dispatchers.IO) { repository.deleteById(id) }

    override suspend fun exists(entity: UserNamePasswordEntity) =
        withContext(Dispatchers.IO) { repository.existsById(entity.id) }

    override suspend fun findByUsername(username: String): UserNamePasswordEntity? {
        val entity = withContext(Dispatchers.IO) { parentRepository.findByUsername(username) }
        if (entity != null) {
            return findById(entity.id)
        }
        return null
    }
}
