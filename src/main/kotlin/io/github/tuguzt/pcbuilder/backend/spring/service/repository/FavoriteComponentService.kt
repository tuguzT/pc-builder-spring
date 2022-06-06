package io.github.tuguzt.pcbuilder.backend.spring.service.repository

import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData

interface FavoriteComponentService {
    suspend fun addFavoriteComponent(user: UserData, component: PolymorphicComponent)

    suspend fun removeFavoriteComponent(user: UserData, component: PolymorphicComponent)
}
