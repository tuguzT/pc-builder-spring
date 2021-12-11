package io.github.tuguzt.pcbuilder.backend.spring.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST API controller for the Octopart API token.
 */
@RestController
@RequestMapping("octopart")
class OctopartController {
    companion object {
        @JvmStatic
        private val token: String = System.getenv("OCTOPART_API_TOKEN")
    }

    /**
     * GET request which returns Octopart API token.
     */
    @GetMapping("token")
    fun token() = "\"$token\""
}
