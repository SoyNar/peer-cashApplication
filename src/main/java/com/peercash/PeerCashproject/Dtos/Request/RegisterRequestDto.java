package com.peercash.PeerCashproject.Dtos.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos del usuario para el registro")
public class RegisterRequestDto {

@NotNull(message = "Documento es obligatorio")
@Pattern(regexp = "^\\d{8}$", message = "El DNI debe contener solo números " +
        "y tener exactamente 8 dígitos")
@JsonProperty("document")
    private String document;

@NotNull(message = "el email no puede estar vacio")
@Email(message = "debe ser un email valido")
@Schema(description = "Correo electrónico del usuario", example = "usuario@example.com")
@JsonProperty("email")
    private String email;

    @NotNull(message = "el nombre no puede estar vacio")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "el apellido no puede estar vacio")
    @JsonProperty("lastname")
    private String lastname;

    @NotNull(message = "la fecha de cumpleaños no puede estar vacia")
    @JsonProperty("birthday")
    private LocalDate birthday;

    @NotNull(message = "la ceutna bancaria es obligatoria")
    @Pattern(regexp = "^\\d{12}$", message = "la cuenta bancaria debe contener solo números " +
            "y tener exactamente 12 dígitos")
    @JsonProperty("bankAccount")
    private String bankAccount;

    @NotNull(message = "El rol es obligatorio")
    @JsonProperty("roles")
    private List<String> roles;

    @NotNull(message = "la ciudad es obligatoria")
    @JsonProperty("city")
    private String city;

    private BigDecimal incomeInstallments;
    private BigDecimal expensesInstallments;
    private String password;



}
