package io.github.tuguzt.pcbuilder.backend.routes

import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import io.github.tuguzt.pcbuilder.domain.repository.user.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.userRoutes() {
    routing {
        readAllUsersRoute()
        readByIdUserRoute()
        saveUserRoute()
    }
}

fun Route.readAllUsersRoute() {
    val userRepository: UserRepository<Nothing?> by inject()

    get("/users/all") {
        val users = when (val result = userRepository.readAll()) {
            is Result.Error -> throw checkNotNull(result.cause)
            is Result.Success -> result.data
        }
        call.respond(users)
    }
}

fun Route.readByIdUserRoute() {
    val userRepository: UserRepository<Nothing?> by inject()

    get("/users/id/{id}") {
        val id = call.parameters["id"]?.let(::NanoId) ?: error("No id was found")
        val user = when (val result = userRepository.readById(id)) {
            is Result.Error -> throw checkNotNull(result.cause)
            is Result.Success -> result.data
        }
        when (user) {
            null -> call.respond(HttpStatusCode.NotFound)
            else -> call.respond(user)
        }
    }
}

fun Route.saveUserRoute() {
    val userRepository: UserRepository<Nothing?> by inject()

    post("users/save") {
        val user: UserData = call.receive()
        when (val result = userRepository.save(user)) {
            is Result.Error -> throw checkNotNull(result.cause)
            is Result.Success -> call.respond(result.data)
        }
    }
}
