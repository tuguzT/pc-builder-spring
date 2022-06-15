package io.github.tuguzt.pcbuilder.backend.data.datasource

import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData

interface ManufacturerDataSource : CrudDataSource<NanoId, ManufacturerData> {
    suspend fun readByName(name: String): Result<ManufacturerData?, Nothing?>
}