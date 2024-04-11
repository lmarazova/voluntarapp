package grupo7.volutarapp.model.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUsuarioVoluntario {

    @NotNull
    private String nombreUsuario;

    @NotNull(message="La contraseña es obligatoria")
    private String contrasenya;
}
