package io.github.tuguzt.pcbuilder.backend.data.datasource.util

import io.github.tuguzt.pcbuilder.backend.data.datasource.util.crud.*
import io.github.tuguzt.pcbuilder.domain.model.Identifiable

internal interface CrudDataSource<Id, T : Identifiable<Id>> :
    ClearDataSource<Id, T>,
    DeleteDataSource<Id, T>,
    ReadAllDataSource<Id, T>,
    ReadByIdDataSource<Id, T>,
    SaveDataSource<Id, T>
