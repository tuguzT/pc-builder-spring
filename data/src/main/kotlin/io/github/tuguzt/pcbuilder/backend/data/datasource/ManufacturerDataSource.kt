package io.github.tuguzt.pcbuilder.backend.data.datasource

import io.github.tuguzt.pcbuilder.backend.data.datasource.util.CrudDataSource
import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData

internal interface ManufacturerDataSource : CrudDataSource<NanoId, ManufacturerData> {
    suspend fun readByName(name: String): Result<ManufacturerData?, Nothing?>
}
