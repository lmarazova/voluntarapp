package grupo7.volutarapp.controller;

import grupo7.volutarapp.model.dtos.RegistroUsuarioVoluntarioForm;
import grupo7.volutarapp.model.entity.ServicioEntity;
import grupo7.volutarapp.model.entity.UsuarioVoluntario;
import grupo7.volutarapp.model.enums.Ubicacion;
import grupo7.volutarapp.repository.ServicioEntityRepository;
import grupo7.volutarapp.repository.UsuarioVoluntarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Transactional

public class RegisterVoluntarioController {
    @Autowired
    private UsuarioVoluntarioRepository usuarioVoluntarioRepository;
    @Autowired
    private ServicioEntityRepository servicioRepository;

    public RegisterVoluntarioController(UsuarioVoluntarioRepository usuarioVoluntarioRepository) {
        this.usuarioVoluntarioRepository = usuarioVoluntarioRepository;
    }

    @GetMapping("/formulario-de-registro-comun")
    public String mostrarFormularioComun() {
        return "formulario-de-registro-comun"; // Devuelve el nombre de la vista Thymeleaf
    }
    @GetMapping("/registrar-usuario-voluntario")
    public String mostrarFormularioRegistroVoluntario(Model model){
        model.addAttribute("usuarioVoluntarioRegisterForm", new RegistroUsuarioVoluntarioForm());
        // Crear una lista para almacenar las ubicaciones con nombres legibles
        List<String> ubicacionesLegibles = new ArrayList<>();
        for (Ubicacion ubicacion : Ubicacion.values()) {
            ubicacionesLegibles.add(ubicacion.toString());
        }
        model.addAttribute("ubicaciones", ubicacionesLegibles); // Agrega las ubicaciones al modelo

        return "registrar-usuario-voluntario";
    }
    @GetMapping("/registrar-usuario-necesitado")
    public String mostrarFormularioRegistroNecesitado(){
        return "registrar-usuario-necesitado";
    }

    @PostMapping("/registrar-usuario-voluntario")
    public ModelAndView handleRegisterRequest(@Valid @ModelAttribute("usuarioVoluntarioRegisterForm")RegistroUsuarioVoluntarioForm registroUsuarioVoluntarioForm,
                                              @RequestParam(name = "habilidades", required = false)String[]habilidades,
                                              BindingResult bindingResult,
                                              ModelAndView modelAndView){

       if(bindingResult.hasErrors()||!registroUsuarioVoluntarioForm.lasContrasenyasCoinciden()){
           modelAndView.addObject("registroUsuarioVoluntarioForm", registroUsuarioVoluntarioForm); // Esto es importante para mostrar los valores ingresados por el usuario en el formulario
           modelAndView.addObject("errorVisible", true);
           modelAndView.addObject("errorMessage", "Las contraseñas no coinciden");
           System.out.println("ERRORS IN BINDINGRESULT: " + bindingResult.getAllErrors());
            return modelAndView;
        }
        System.out.println("errorVisible: " + modelAndView.getModel().get("errorVisible"));
        System.out.println("errorMessage: " + modelAndView.getModel().get("errorMessage"));

        // Verificar si se seleccionaron habilidades
        if (habilidades != null) {
            registroUsuarioVoluntarioForm.setHabilidades(new HashSet<>(Arrays.asList(habilidades)));



            // Guardar las habilidades en el usuario voluntario
            for (String habilidad : habilidades) {
                ServicioEntity servicio = servicioRepository.findByServicio(habilidad);
                if (servicio != null) {
                    servicioRepository.save(servicio);
                }
            }
        } else {
            // Si no se seleccionaron habilidades, establecer un conjunto vacío
            registroUsuarioVoluntarioForm.setHabilidades(new HashSet<>());
            System.out.println("No se seleccionaron habilidades.");
        }


        if(habilidades != null){
            for(String habilidad : habilidades){
                ServicioEntity servicio = servicioRepository.findByServicio(habilidad);
                if(servicio!=null){
                    servicioRepository.save(servicio);
                }
            }
        }

        UsuarioVoluntario usuarioVoluntario = new UsuarioVoluntario();




        usuarioVoluntario.setNombreUsuario(registroUsuarioVoluntarioForm.getNombreUsuario());
        usuarioVoluntario.setContrasenya(registroUsuarioVoluntarioForm.getContrasenya());
        usuarioVoluntario.setNombre(registroUsuarioVoluntarioForm.getNombre());
        usuarioVoluntario.setApellido(registroUsuarioVoluntarioForm.getApellido());
        usuarioVoluntario.setCorreoElectronico(registroUsuarioVoluntarioForm.getCorreoElectronico());
        usuarioVoluntario.setUbicacion(Ubicacion.valueOf(registroUsuarioVoluntarioForm.getUbicacion()));
        if(habilidades != null){
            for(String habilidad: habilidades){
                ServicioEntity servicio = servicioRepository.findByServicio(habilidad);
                if(servicio != null){
                    servicio.getUsuariosVoluntarios().add(usuarioVoluntario);
                    usuarioVoluntario.getServiciosOfrecidos().add(servicio); // Si la relación es bidireccional, agrega el servicio al usuario
                    servicioRepository.save(servicio); // Guardar los cambios en el servicio

                }
            }
        }

        usuarioVoluntarioRepository.save(usuarioVoluntario);
        modelAndView.addObject("usuarioVoluntario", usuarioVoluntario);
        return modelAndView;

    }
    @ModelAttribute(name = "registroUsuarioVoluntarioForm")
    public RegistroUsuarioVoluntarioForm registroUsuarioVoluntarioForm(){
        return new RegistroUsuarioVoluntarioForm();
    }
}
