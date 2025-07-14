package com.challange.coupon.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de error")
public record ErrorResponse(
        @Schema(description = "Código de error", example = "ITEM_NOT_FOUND")
        String code,

        @Schema(description = "Mensaje de error", example = "El ítem con ID MLA123 no fue encontrado")
        String message,

        @Schema(description = "ID del ítem relacionado al error, si aplica", example = "MLA123", nullable = true)
        String itemId
) {
    public ErrorResponse(String code, String message) {
        this(code, message, null);
    }
}