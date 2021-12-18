package io.github.tuguzt.pcbuilder.backend.spring.service.impl

import io.github.tuguzt.pcbuilder.backend.spring.model.UserNamePasswordEntity
import io.github.tuguzt.pcbuilder.backend.spring.repository.UserNamePasswordRepository
import io.github.tuguzt.pcbuilder.backend.spring.service.UserNamePasswordService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserNamePasswordServiceImpl(private val repository: UserNamePasswordRepository) : UserNamePasswordService {
    override suspend fun save(entity: UserNamePasswordEntity) = withContext(Dispatchers.IO) { repository.save(entity) }

    override suspend fun getAll(): List<UserNamePasswordEntity> = withContext(Dispatchers.IO) { repository.findAll() }

    override suspend fun findById(id: String) = withContext(Dispatchers.IO) { repository.findByIdOrNull(id) }

    override suspend fun findByUsername(username: String) =
        withContext(Dispatchers.IO) { repository.findByUsername(username) }
}
