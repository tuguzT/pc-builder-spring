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
    /**
     * GET request which returns Octopart API token.
     */
    @GetMapping("token")
    suspend fun token(): String = System.getenv("OCTOPART_API_TOKEN")
}
