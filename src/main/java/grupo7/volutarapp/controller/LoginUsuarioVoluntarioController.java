package grupo7.volutarapp.controller;

import grupo7.volutarapp.model.dtos.LoginUsuarioVoluntario;
import grupo7.volutarapp.model.entity.UsuarioVoluntario;
import grupo7.volutarapp.service.UsuarioVoluntarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUsuarioVoluntarioController {
    @Autowired
    private HttpSession session;
    @Autowired
    private UsuarioVoluntarioService usuarioVoluntarioService;

    @GetMapping("/inicio-sesion-formulario-usuario-voluntario")
    public String mostrarLoginVoluntarioForm(ModelAndView modelAndView){
         LoginUsuarioVoluntario loginVoluntario = new LoginUsuarioVoluntario();
        if(session.getAttribute("voluntarioId")!=null){
            return "redirect:/home-voluntario";
        }
        modelAndView.addObject("loginUsuarioVoluntario", new LoginUsuarioVoluntario());
        return "inicio-sesion-formulario-usuario-voluntario";
    }
    @PostMapping("/inicio-sesion-formulario-usuario-voluntario")
    public ModelAndView handleLoginRequest(@Valid @ModelAttribute("loginUsuarioVoluntario")LoginUsuarioVoluntario loginUsuarioVoluntario,
                                           BindingResult bindingResult,
                                           ModelAndView modelAndView,
                                           HttpServletRequest request){
        if(bindingResult.hasErrors()){
            modelAndView.addObject("errorVisible", true);
            return modelAndView;
        }
        UsuarioVoluntario usuarioVoluntario = usuarioVoluntarioService.getUsuarioVoluntarioByNombreUsuario(loginUsuarioVoluntario.getNombreUsuario());
        if(usuarioVoluntario != null && usuarioVoluntarioService.contrasenyaValida(usuarioVoluntario, loginUsuarioVoluntario.getContrasenya())){
            HttpSession httpSession=request.getSession();
            session.setAttribute("voluntarioId", usuarioVoluntario.getId());
            modelAndView.setViewName("redirect:/home-voluntario");
            return modelAndView;
        }
        else{
            modelAndView.addObject("error", "El nombre o la contraseña son incorrectos");
            return modelAndView;
        }


        }
        @ModelAttribute(name = "loginUsuarioVoluntario")
        public LoginUsuarioVoluntario loginUsuarioVoluntario(){
            return new LoginUsuarioVoluntario();
        }
    }

