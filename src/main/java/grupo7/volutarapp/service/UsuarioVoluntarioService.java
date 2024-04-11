package grupo7.volutarapp.service;

import grupo7.volutarapp.model.entity.UsuarioVoluntario;
import grupo7.volutarapp.repository.UsuarioVoluntarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioVoluntarioService {
    /*private HttpServletRequest request;*/
    @Autowired
    private UsuarioVoluntarioRepository usuarioVoluntarioRepository;

    public void loginVoluntario(Long voluntarioId, HttpSession session){
        session.setAttribute("voluntarioId", voluntarioId);
    }

    public boolean isUsuarioVoluntarioLoggedIn(HttpSession session) {
        return session != null && session.getAttribute("voluntarioId") != null;
    }

    public UsuarioVoluntario getUsuarioVoluntarioByNombreUsuario(String nombreUsuario){
        return usuarioVoluntarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public boolean contrasenyaValida(UsuarioVoluntario usuarioVoluntario, String contrasenya){
        return usuarioVoluntario.getContrasenya().equals(contrasenya);
    }
}

