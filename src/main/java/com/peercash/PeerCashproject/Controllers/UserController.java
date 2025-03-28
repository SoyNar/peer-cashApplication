package com.peercash.PeerCashproject.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peercash.PeerCashproject.Dtos.Request.RegisterRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.RegisterResponseDto;
import com.peercash.PeerCashproject.Dtos.Response.SeeAllLoansResponseDto;
import com.peercash.PeerCashproject.Service.IService.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/api/peer-cash/auth")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final ObjectMapper objectMapper;

    @Operation(summary = "Registrar un usuario con imágenes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado con éxito",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RegisterResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(
            @RequestPart("userData") String userDataJson,

            @RequestPart("documentImage")
            @Parameter(description = "Imagen del documento (PDF, JPG, PNG)")
            MultipartFile documentImage,

            @RequestPart("bankStatementImage")
            @Parameter(description = "Imagen del extracto bancario (PDF, JPG, PNG)")
            MultipartFile bankStatementImage)  {

        try {

            RegisterRequestDto requestDto = objectMapper.readValue(userDataJson, RegisterRequestDto.class);

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(requestDto);

            if (!violations.isEmpty()) {
                Map<String, String> errors = new HashMap<>();
                violations.forEach(v -> errors.put(v.getPropertyPath().toString(), v.getMessage()));
                return ResponseEntity.badRequest().body(errors);
            }

            RegisterResponseDto responseDto = this.userService.registerUser(requestDto,
                    documentImage,
                    bankStatementImage);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar usuario: " + e.getMessage());
        }
    }


}
