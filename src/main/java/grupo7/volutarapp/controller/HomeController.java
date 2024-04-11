package grupo7.volutarapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller/*
@RequestMapping("/")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor*/
public class HomeController extends BaseController{
    @GetMapping("/home-voluntario")
    public String homeVoluntario() {
        return "home-voluntario"; // Este es el nombre de la vista que quieres mostrar
    }


    /*private UsuarioVoluntarioServiceImpl usuarioVoluntarioService;
    private UsuarioNecesitadoServiceImpl usuarioNecesitadoService;
    @GetMapping("/")
    public String getIndex() {
        boolean usuarioVoluntarioConectado = usuarioVoluntarioService.estaConectado();
        // Aquí puedes usar usuarioVoluntarioService.estaConectado() sin preocuparte por la NullPointerException
        return "index";
    }*/
   /* @GetMapping
    private ModelAndView getIndex(){
        ModelAndView modelAndView = super.view("index");
        boolean usuarioVoluntarioConectado = usuarioVoluntarioService.estaConectado();
        modelAndView.addObject("usuarioVoluntarioConectado", usuarioVoluntarioConectado);
        boolean usuarioNecesitadoConectado = usuarioNecesitadoService.estaConectado();
        modelAndView.addObject("usuarioNecesitadoConectado", usuarioNecesitadoConectado);
        return modelAndView;
    }*/
}
