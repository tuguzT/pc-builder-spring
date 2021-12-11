package io.github.tuguzt.pcbuilder.backend.spring.controller

import io.github.tuguzt.pcbuilder.backend.spring.model.UserEntity
import io.github.tuguzt.pcbuilder.backend.spring.service.UserService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("users")
class UserController(private val service: UserService) {
    @GetMapping
    suspend fun index() = service.getAll()

    @GetMapping("id/{id}")
    suspend fun findById(@PathVariable id: String): UserEntity? {
        logger.info { "Requested component with ID $id" }
        return service.findById(id).apply {
            this?.let {
                logger.info { "Found component with ID $id" }
                return@apply
            }
            logger.info { "Component with ID $id not found" }
        }
    }

    @GetMapping("username/{username}")
    suspend fun findByUsername(@PathVariable username: String): UserEntity? {
        logger.info { "Requested component with username $username" }
        return service.findByUsername(username).apply {
            this?.let {
                logger.info { "Found component with username $username" }
                return@apply
            }
            logger.info { "Component with username $username not found" }
        }
    }

    @PostMapping("save")
    suspend fun save(@RequestBody entity: UserEntity) {
        service.save(entity)
        logger.info { "Inserted component with ID ${entity.id}" }
    }
}
