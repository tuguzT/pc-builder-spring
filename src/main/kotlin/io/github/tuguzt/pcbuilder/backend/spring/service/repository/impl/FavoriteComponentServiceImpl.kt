package io.github.tuguzt.pcbuilder.backend.spring.service.repository.impl

import io.github.tuguzt.pcbuilder.backend.spring.controller.exceptions.NotFoundException
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.toData
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
    private val componentRepository: ComponentRepository,
    private val userRepository: UserRepository,
) : FavoriteComponentService {

    override suspend fun addFavoriteComponent(
        user: UserData,
        component: PolymorphicComponent,
    ): PolymorphicComponent = withContext(Dispatchers.IO) {
        val componentEntity = componentRepository.findByIdOrNull(component.id.toString()) ?: throw NotFoundException()
        val userEntity = userRepository.findByIdOrNull(user.id.toString()) ?: throw NotFoundException()
        componentEntity.favorites += userEntity
        componentRepository.save(componentEntity)
    }.toData(user)

    override suspend fun removeFavoriteComponent(
        user: UserData,
        component: PolymorphicComponent,
    ): PolymorphicComponent = withContext(Dispatchers.IO) {
        val componentEntity = componentRepository.findByIdOrNull(component.id.toString()) ?: throw NotFoundException()
        val userEntity = userRepository.findByIdOrNull(user.id.toString()) ?: throw NotFoundException()
        componentEntity.favorites -= userEntity
        componentRepository.save(componentEntity)
    }.toData(user)

    override suspend fun getFavoriteComponents(user: UserData): List<PolymorphicComponent> =
        userRepository.findByIdOrNull(user.id.toString())?.let { userEntity ->
            userEntity.favoriteComponents.map { it.toData(user) }
        } ?: listOf()
}
