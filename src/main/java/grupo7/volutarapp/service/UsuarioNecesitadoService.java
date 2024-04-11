package grupo7.volutarapp.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioNecesitadoService {
    private HttpServletRequest request;

    public boolean estaConectado() {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("userId") !=  null;
    }
    public void conectarUsuarioNecesitado(Long userId){
        HttpSession session = request.getSession(true);
        session.setAttribute("userId", userId);
    }

    public void desconectarUsuarioNecesitado(){
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.removeAttribute("userId");
            session.invalidate();
        }
    }
}
