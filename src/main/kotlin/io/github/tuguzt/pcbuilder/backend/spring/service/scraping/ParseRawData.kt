package io.github.tuguzt.pcbuilder.backend.spring.service.scraping

data class ParseRawData(
    val name: String,
    val manufacturerName: String,
    val data: Map<String, String>,
)
