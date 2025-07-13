package com.challange.coupon.web.controller;

import com.challange.coupon.application.dto.response.ErrorResponse;
import com.challange.coupon.domain.port.in.CouponUseCase;
import com.challange.coupon.application.dto.request.CouponRequest;
import com.challange.coupon.application.dto.response.CouponResponse;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind. annotation.*;
import io.swagger.v3.oas.annotations.Operation;
@RestController
@RequestMapping("/coupon")
@Tag(name = "Coupon API", description = "API para el manejo de cupones de Mercado Libre")
public class CouponController {
    private final CouponUseCase couponUseCase;
    public CouponController(CouponUseCase couponUseCase) {
        this.couponUseCase = couponUseCase;
    }
    /*
    @Operation(summary = "Aplicar cupón",
            description = "Calcula la mejor combinación de ítems para un cupón")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cálculo exitoso",
                    content = @Content(schema = @Schema(implementation = CouponResponse.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Ítem no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Precio inválido",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "502", description = "Error en API externa",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PostMapping
    public CouponResponse applyCoupon(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Solicitud de aplicación de cupón",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CouponRequest.class)))
            @RequestBody CouponRequest request,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        var result = couponUseCase.calculateBestItems(request.getItem_ids(), request.getAmount(), token);
        return new CouponResponse(result.getItemIds(), result.getTotal());
    }*/

    // Nivel 1: Endpoint exacto como se solicita
    @Operation(summary = "Calcular items óptimos para cupón",
            description = "Devuelve la combinación de items que maximiza el uso del cupón")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cálculo exitoso",
                    content = @Content(schema = @Schema(implementation = CouponResponse.class))),
            @ApiResponse(responseCode = "400", description = "Request inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @PostMapping
    public CouponResponse coupon(
            @RequestBody @Valid CouponRequest request,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        var result = couponUseCase.calculateBestItems(request.getItem_ids(), request.getAmount(), token);
        return new CouponResponse(result.getItemIds(), result.getTotal());
    }
}
