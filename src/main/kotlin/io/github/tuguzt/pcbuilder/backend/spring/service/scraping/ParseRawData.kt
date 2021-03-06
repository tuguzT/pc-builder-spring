package io.github.tuguzt.pcbuilder.backend.spring.service.scraping

data class ParseRawData(
    val name: String,
    val description: String,
    val manufacturerName: String,
    val imageUris: List<String>,
    val data: Map<String, String>,
)
