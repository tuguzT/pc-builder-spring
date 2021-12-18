package io.github.tuguzt.pcbuilder.backend.spring.controller

import io.github.tuguzt.pcbuilder.backend.spring.model.UserView
import io.github.tuguzt.pcbuilder.backend.spring.model.toView
import io.github.tuguzt.pcbuilder.backend.spring.service.UserNamePasswordService
import io.github.tuguzt.pcbuilder.backend.spring.service.UserOAuth2Service
import io.github.tuguzt.pcbuilder.domain.model.user.User
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("users")
class UserController(
    private val userNamePasswordService: UserNamePasswordService,
    private val userOAuth2Service: UserOAuth2Service,
) {
    @GetMapping
    suspend fun index(): List<UserView> = (userNamePasswordService.getAll() + userOAuth2Service.getAll())
        .map(User::toView)
        .sortedBy(UserView::id)

    @GetMapping("id/{id}")
    suspend fun findById(@PathVariable id: String): ResponseEntity<UserView> {
        logger.info { "Requested user with ID $id" }
        val namePasswordUser = userNamePasswordService.findById(id)
        if (namePasswordUser == null) {
            val oauth2user = userOAuth2Service.findById(id)
            if (oauth2user == null) {
                logger.info { "User with ID $id not found" }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            }
            logger.info { "Found user with ID $id" }
            return ResponseEntity.ok(oauth2user.toView())
        }
        logger.info { "Found user with ID $id" }
        return ResponseEntity.ok(namePasswordUser.toView())
    }

    @GetMapping("username/{username}")
    suspend fun findByUsername(@PathVariable username: String): ResponseEntity<UserView> {
        logger.info { "Requested user with username $username" }
        val user = userNamePasswordService.findByUsername(username)
        if (user == null) {
            logger.info { "User with username $username not found" }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
        logger.info { "Found user with username $username" }
        return ResponseEntity.ok(user.toView())
    }
}
