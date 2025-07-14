package com.challange.coupon.web.controller;

import com.challange.coupon.application.dto.request.FavoriteRequest;
import com.challange.coupon.application.dto.response.ErrorResponse;
import com.challange.coupon.application.dto.response.FavoriteResponse;
import com.challange.coupon.application.service.FavoriteUserService;
import com.challange.coupon.application.service.FavoriteStatsService;
import com.challange.coupon.domain.model.ItemFavoriteStats;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupon")
public class StatsController {

    private final FavoriteStatsService favoriteStatsService;
    private final FavoriteUserService favoriteUserService;

    public StatsController(FavoriteStatsService favoriteStatsService, FavoriteUserService favoriteUserService) {
        this.favoriteStatsService = favoriteStatsService;
        this.favoriteUserService = favoriteUserService;
    }
    // Nivel 2:
    @Operation(
            summary = "Obtener top 5 items favoritos",
            description = "Devuelve los 5 items más marcados como favoritos"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista obtenida",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ItemFavoriteStats.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/stats")
    public List<ItemFavoriteStats> stats() {
        return favoriteStatsService.getTop5Favorites();
    }

    @Operation(summary = "Agregar ítem a favoritos",
            description = "Registra un ítem como favorito para un usuario")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ítem agregado a favoritos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FavoriteResponse.class),
                            examples = @ExampleObject(name = "AddFavoriteSuccess",
                                    summary = "Respuesta exitosa al agregar favorito",
                                    value = "{ \"message\": \"Ítem agregado exitosamente\", \"newFavoriteCount\": 10 }")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(name = "InvalidItemIdRequest",
                                    summary = "Falta el campo item_id",
                                    value = "{ \"code\": \"INVALID_REQUEST\", \"message\": \"El campo item_id es obligatorio\", \"itemId\": null }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "El ítem ya está en favoritos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(name = "DuplicateItemExample",
                                    summary = "El ítem ya está marcado como favorito",
                                    value = "{ \"code\": \"DUPLICATE_ITEM\", \"message\": \"El ítem MLA123 ya está registrado como favorito\", \"itemId\": \"MLA123\" }")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error al procesar la solicitud",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(name = "GenericErrorExample",
                                    summary = "Error genérico del servidor",
                                    value = "{ \"code\": \"GENERIC-ERROR\", \"message\": \"Error interno del servidor\", \"itemId\": null }")
                    )
            )
    })
    // Nivel 2: Complementa para acumular favoritos
    @PostMapping("/user/favorite")
    public ResponseEntity<FavoriteResponse> addFavorite(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para agregar favorito",
                    required = true,
                    content = @Content(schema = @Schema(implementation = FavoriteRequest.class)))
            @RequestBody @Valid FavoriteRequest request) {

        FavoriteResponse response = favoriteUserService.addFavorite(
                request.getUserId(),
                request.getItemId()
        );
        return ResponseEntity.ok(response);
    }
}
