package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.motherboard

import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardUsbHeaders
import javax.persistence.Embeddable

@Embeddable
data class MotherboardUsbHeadersEmbeddable(
    private val usb2HeaderCount: UByte,
    private val usb3gen1HeaderCount: UByte,
    private val usb3gen2HeaderCount: UByte,
    private val usb3gen2x2HeaderCount: UByte,
) {
    fun toMotherboardUsbHeaders(): MotherboardUsbHeaders =
        MotherboardUsbHeaders(usb2HeaderCount, usb3gen1HeaderCount, usb3gen2HeaderCount, usb3gen2x2HeaderCount)
}

fun MotherboardUsbHeaders.toEmbeddable(): MotherboardUsbHeadersEmbeddable =
    MotherboardUsbHeadersEmbeddable(usb2HeaderCount, usb3gen1HeaderCount, usb3gen2HeaderCount, usb3gen2x2HeaderCount)
