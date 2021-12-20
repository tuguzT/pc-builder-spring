package io.github.tuguzt.pcbuilder.backend.spring.controller

import io.github.tuguzt.pcbuilder.backend.spring.model.octopart.SearchResult
import io.github.tuguzt.pcbuilder.backend.spring.model.octopart.toResults
import io.github.tuguzt.pcbuilder.backend.spring.retrofit.OctopartAPI
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
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
@Tag(name = "Octopart API", description = "Конечные сетевые точки обращения для использования Octopart API")
class OctopartController : KoinComponent {
    companion object {
        @JvmStatic
        private val token: String = System.getenv("OCTOPART_API_TOKEN")
    }

    private val octopartAPI: OctopartAPI by inject()

    @GetMapping("search")
    @Operation(summary = "Поиск компонентов", description = "Поиск компонентов ПК по запросу")
    suspend fun search(
        @RequestParam
        @Parameter(name = "Запрос для поиска")
        query: String,
        @RequestParam
        @Parameter(name = "Индекс первого результата поиска в списке найденных компонентов")
        start: Int,
        @RequestParam
        @Parameter(name = "Максимальное количество компонентов, возвращаемое запросом")
        limit: Int,
    ): ResponseEntity<List<SearchResult>> {
        logger.info { "Requested query `$query` with start $start and limit $limit" }
        val response = octopartAPI.searchQuery(query, token, start, limit).await()
        return ResponseEntity.ok(response.toResults())
    }
}
