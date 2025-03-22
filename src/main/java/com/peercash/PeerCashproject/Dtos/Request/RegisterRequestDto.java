package com.peercash.PeerCashproject.Dtos.Request;

import com.peercash.PeerCashproject.Models.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class RegisterRequestDto {
@NotNull(message = "Documento es obligatorio")
@Pattern(regexp = "^\\d{8}$", message = "El DNI debe contener solo números " +
        "y tener exactamente 8 dígitos")
    private String document;
@NotNull(message = "el email no puede estar vacio")
@Email(message = "debe ser un email valido")
    private String email;

    @NotNull(message = "el nombre no puede estar vacio")
    private String name;
    @NotNull(message = "el apellido no puede estar vacio")
    private String lastname;
    @NotNull(message = "la fecha de cumpleaños no puede estar vacia")
    private LocalDate birthday;
    @NotNull(message = "la ceutna bancaria es obligatoria")
    @Pattern(regexp = "^\\d{12}$", message = "la cuenta bancaria debe contener solo números " +
            "y tener exactamente 12 dígitos")
    private String bankAccount;
    @NotNull(message = "el tipo de usuario  es obligatoria")
    private String userType;
    @NotNull(message = "El rol es obligatorio")
    private List<String> roles;
    @NotNull(message = "la ciudad es obligatoria")
    private String city;


}
