package com.peercash.PeerCashproject.Controllers;

import com.peercash.PeerCashproject.Dtos.Request.RegisterRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.RegisterResponseDto;
import com.peercash.PeerCashproject.Service.IUserService;
import com.peercash.PeerCashproject.Service.Impl.CloudinaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/peer-cash/auth")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final CloudinaryService cloudinaryService;

    @Operation(summary = "Registrar un usuario con imágenes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado con éxito",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RegisterResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerUser (
            @Valid
            @RequestPart("userData") RegisterRequestDto requestDto,
            @Parameter(description = "Imagen del documento")
            @RequestPart("documentImage") MultipartFile documentImage,
            @Parameter(description = "Imagen del extracto bancario")
            @RequestPart("bankStatementImage") MultipartFile bankStatementImage){
        try{

            RegisterResponseDto responseDto = this.userService.registerUser(requestDto,documentImage,bankStatementImage);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar usuario: " + e.getMessage());
        }
    }


}
