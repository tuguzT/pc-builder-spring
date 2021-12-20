package io.github.tuguzt.pcbuilder.backend.spring.controller

import io.github.tuguzt.pcbuilder.backend.spring.model.octopart.SearchResult
import io.github.tuguzt.pcbuilder.backend.spring.model.octopart.toResults
import io.github.tuguzt.pcbuilder.backend.spring.retrofit.OctopartAPI
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import retrofit2.await

private val logger = KotlinLogging.logger {}

/**
 * REST API controller for the Octopart API.
 */
@RestController
@RequestMapping("octopart")
class OctopartController : KoinComponent {
    companion object {
        @JvmStatic
        private val token: String = System.getenv("OCTOPART_API_TOKEN")
    }

    private val octopartAPI: OctopartAPI by inject()

    @GetMapping("search")
    suspend fun search(
        @RequestParam query: String,
        @RequestParam start: Int,
        @RequestParam limit: Int,
    ): ResponseEntity<List<SearchResult>> {
        logger.info { "Requested query `$query` with start $start and limit $limit" }
        val response = octopartAPI.searchQuery(query, token.orEmpty(), start, limit).await()
        return ResponseEntity.ok(response.toResults())
    }
}
