package io.github.tuguzt.pcbuilder.backend.data.datasource.local.model

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import org.jetbrains.exposed.sql.CharColumnType
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.IColumnType
import org.jetbrains.exposed.sql.Table

internal class NanoIdColumnType(
    @Suppress("MemberVisibilityCanBePrivate")
    internal val charColumnType: CharColumnType = CharColumnType(colLength = 21),
) : IColumnType by charColumnType {

    override fun valueFromDB(value: Any): NanoId = charColumnType.valueFromDB(value).toString().let(::NanoId)

    override fun notNullValueToDB(value: Any): Any = charColumnType.notNullValueToDB(valueUnwrap(value))

    override fun nonNullValueToString(value: Any): String = charColumnType.nonNullValueToString(valueUnwrap(value))

    private fun valueUnwrap(value: Any) = (value as? NanoId)?.toString() ?: value
}

internal fun Table.nanoId(name: String = "id"): Column<NanoId> = registerColumn(name, NanoIdColumnType())
