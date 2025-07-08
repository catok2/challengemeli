package com.challange.coupon.web.controller;

import com.challange.coupon.domain.port.in.CouponUseCase;
import com.challange.coupon.application.dto.CouponRequest;
import com.challange.coupon.application.dto.CouponResponse;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
@RestController
@RequestMapping("/coupon")
@Tag(name = "Coupon API", description = "API para el manejo de cupones de Mercado Libre")
public class CouponController {
    private final CouponUseCase couponUseCase;
    public CouponController(CouponUseCase couponUseCase) {
        this.couponUseCase = couponUseCase;
    }

    @Operation(
            summary = "Aplicar cupón",
            description = "Calcula los mejores ítems para maximizar el uso del cupón sin exceder el monto"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cálculo exitoso",
                    content = @Content(schema = @Schema(implementation = CouponResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parámetros inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })

    @PostMapping
    public CouponResponse applyCoupon(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Solicitud de aplicación de cupón",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CouponRequest.class)))
            @RequestBody CouponRequest request) {

        var result = couponUseCase.calculateBestItems(request.getItem_ids(), request.getAmount());
        return new CouponResponse(result.getItemIds(), result.getTotal());
    }
}
