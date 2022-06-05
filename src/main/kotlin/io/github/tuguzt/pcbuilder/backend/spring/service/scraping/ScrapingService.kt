package io.github.tuguzt.pcbuilder.backend.spring.service.scraping

import org.springframework.stereotype.Service
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Service
class ScrapingService(private val caseScrapingService: CaseScrapingService) {
    @PostConstruct
    private fun start() {
        caseScrapingService.start()
    }

    @PreDestroy
    private fun stop() {
        caseScrapingService.stop()
    }
}
