package io.github.tuguzt.pcbuilder.backend.spring.controller

import io.github.tuguzt.pcbuilder.backend.spring.model.UserView
import io.github.tuguzt.pcbuilder.backend.spring.model.toView
import io.github.tuguzt.pcbuilder.backend.spring.service.UserService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("users")
class UserController(private val service: UserService) {
    @GetMapping
    suspend fun index(): List<UserView> = service.getAll().map { it.toView() }

    @GetMapping("id/{id}")
    suspend fun findById(@PathVariable id: String): UserView? {
        logger.info { "Requested user with ID $id" }
        return service.findById(id)?.toView().apply {
            this?.let {
                logger.info { "Found user with ID $id" }
                return@apply
            }
            logger.info { "Component with ID $id not found" }
        }
    }

    @GetMapping("username/{username}")
    suspend fun findByUsername(@PathVariable username: String): UserView? {
        logger.info { "Requested user with username $username" }
        return service.findByUsername(username)?.toView().apply {
            this?.let {
                logger.info { "Found user with username $username" }
                return@apply
            }
            logger.info { "User with username $username not found" }
        }
    }
}
