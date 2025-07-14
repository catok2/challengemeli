package com.challange.coupon.web.controller;

import com.challange.coupon.application.dto.request.FavoriteRequest;
import com.challange.coupon.application.dto.response.ErrorResponse;
import com.challange.coupon.application.dto.response.FavoriteResponse;
import com.challange.coupon.application.service.FavoriteUserService;
import com.challange.coupon.application.service.FavoriteStatsService;
import com.challange.coupon.domain.model.ItemFavoriteStats;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
    @Operation(summary = "Obtener top 5 items favoritos",
            description = "Devuelve los 5 items más marcados como favoritos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida",
                    content = @Content(schema = @Schema(implementation = ItemFavoriteStats[].class))),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @GetMapping("/stats")
    public List<ItemFavoriteStats> stats() {
        return favoriteStatsService.getTop5Favorites();
    }

    @Operation(summary = "Agregar ítem a favoritos",
            description = "Registra un ítem como favorito para un usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ítem agregado a favoritos",
                    content = @Content(schema = @Schema(implementation = FavoriteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "El ítem ya está en favoritos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error al procesar la solicitud",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
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
