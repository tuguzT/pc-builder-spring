package io.github.tuguzt.pcbuilder.backend.spring.service.repository.impl

import io.github.tuguzt.pcbuilder.backend.spring.model.toEntity
import io.github.tuguzt.pcbuilder.backend.spring.repository.component.ComponentRepository
import io.github.tuguzt.pcbuilder.backend.spring.repository.user.UserRepository
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.FavoriteComponentService
import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FavoriteComponentServiceImpl(
    private val repository: ComponentRepository,
    private val userRepository: UserRepository,
) : FavoriteComponentService {

    override suspend fun addFavoriteComponent(user: UserData, component: PolymorphicComponent) {
        withContext(Dispatchers.IO) {
            userRepository.findByIdOrNull(user.id)?.let { userEntity ->
                val favorites = repository.findByIdOrNull(component.id.toString())?.favorites ?: setOf()
                val componentEntity = component.toEntity(favorites = favorites + userEntity)
                repository.save(componentEntity)
            }
        }
    }

    override suspend fun removeFavoriteComponent(user: UserData, component: PolymorphicComponent) {
        withContext(Dispatchers.IO) {
            userRepository.findByIdOrNull(user.id)?.let { userEntity ->
                val favorites = repository.findByIdOrNull(component.id.toString())?.favorites ?: setOf()
                val componentEntity = component.toEntity(favorites = favorites - userEntity)
                repository.save(componentEntity)
            }
        }
    }
}
