package com.challange.coupon.web.controller;

import com.challange.coupon.application.dto.response.ErrorResponse;
import com.challange.coupon.domain.port.in.CouponUseCase;
import com.challange.coupon.application.dto.request.CouponRequest;
import com.challange.coupon.application.dto.response.CouponResponse;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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


    // Nivel 1:
    @Operation(summary = "Calcular items óptimos para cupón",
            description = "Devuelve la combinación de items que maximiza el uso del cupón")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cálculo exitoso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CouponResponse.class),
                            examples = @ExampleObject(value = "{ \"item_ids\": [\"MLA123\", \"MLA456\"], \"total\": 1500.0 }")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Request inválido",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value ="{ \"code\": \"INVALID_REQUEST\", \"message\": \"El campo 'amount' debe ser mayor a cero\", \"itemId\": null }")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{ \"code\": \"CALCULATION_ERROR\", \"message\": \"Error al calcular la combinación óptima\", \"itemId\": null }")
                    )
            )
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
