package io.github.tuguzt.pcbuilder.backend.spring.controller

import io.github.tuguzt.pcbuilder.backend.spring.service.repository.CaseService
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.cases.Case
import io.github.tuguzt.pcbuilder.domain.model.component.cases.data.CaseData
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * REST API controller of the server for [PC cases][Case].
 */
@RestController
@RequestMapping("components/cases/")
@Tag(name = "Корпусы ПК", description = "Конечные сетевые точки обращения данных корпусов ПК системы")
class CaseController(private val service: CaseService) {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    /**
     * GET request which returns all PC cases.
     */
    @GetMapping("all")
    @Operation(summary = "Все корпусы", description = "Получение списка всех корпусов ПК в системе")
    suspend fun all(): List<CaseData> {
        logger.info { "Requested all cases" }
        return service.getAll()
    }

    /**
     * GET request which returns all PC cases with paging.
     */
    @GetMapping("paged")
    @Operation(summary = "Все корпусы", description = "Получение списка всех корпусов ПК в системе постранично")
    suspend fun paged(
        @RequestParam
        @Parameter(name = "Номер страницы")
        page: Int,
        @RequestParam
        @Parameter(name = "Количество элементов в странице")
        size: Int,
    ): List<CaseData> = service.getAll(PageRequest.of(page, size))

    /**
     * GET request which returns case found by [id], if any.
     */
    @GetMapping("id/{id}")
    @Operation(
        summary = "Поиск по идентификатору",
        description = "Поиск корпуса ПК в системе по его идентификатору",
    )
    suspend fun findById(
        @PathVariable
        @Parameter(name = "Идентификатор корпуса ПК")
        id: NanoId,
    ): ResponseEntity<CaseData> {
        logger.info { "Requested case with ID $id" }
        val component = service.findById(id)
        if (component == null) {
            logger.info { "Case with ID $id not found" }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
        logger.info { "Found case with ID $id" }
        return ResponseEntity.ok(component)
    }

    /**
     * GET request which returns case found by [name], if any.
     */
    @GetMapping("name/{name}")
    @Operation(summary = "Поиск по названию", description = "Поиск корпуса ПК в системе по его названию")
    suspend fun findByName(
        @PathVariable
        @Parameter(name = "Название корпуса ПК")
        name: String,
    ): ResponseEntity<CaseData> {
        logger.info { "Requested case with name $name" }
        val component = service.findByName(name)
        if (component == null) {
            logger.info { "Case with name $name not found" }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
        logger.info { "Found case with name $name" }
        return ResponseEntity.ok(component)
    }
}
