package mx.uv.desarrolloweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class VistaController {
    @GetMapping("/")
    public String Inicio(){
        return "redirect:/index.html";
    }
}
