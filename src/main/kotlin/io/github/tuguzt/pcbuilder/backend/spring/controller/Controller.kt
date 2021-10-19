package io.github.tuguzt.pcbuilder.backend.spring.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {
    @GetMapping
    fun index() = "Hello, World!"
}
