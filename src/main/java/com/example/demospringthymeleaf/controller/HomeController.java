package com.example.demospringthymeleaf.controller;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demospringthymeleaf.model.Usuario;
import com.example.demospringthymeleaf.repository.UsuarioRepository;

import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;


	@GetMapping("/")
	public String home(Model model) {
	    model.addAttribute("mensagem", "Bem-vindo ao meu projeto Spring Boot com Thymeleaf!");

	    model.addAttribute("frases", List.of(
	        "Você é incrível!",
	        "Bia é maravilhosa",
	        "Vai tomar no cu java"
	    ));

	    return "home"; 
	}
   @GetMapping("/saudacao") 
	public String saudacao(Model model) {
		model.addAttribute("nomeUsuario", "Visitante");
		return "saudacao"; 
	}

	@GetMapping("/formulario-usuario") 
    public String exibirFormulario(Model model) {
       
        model.addAttribute("usuario", new Usuario());
        return "formulario-usuario"; 
    }
	
	@GetMapping("/resultado-formulario")
	public String mostrarResultado() {
	    return "resultado-formulario";  // Renderiza o arquivo resultado-formulario.html
	}
	
	@GetMapping("/lista-usuarios")
	public String listarUsuarios(Model model) {
	    model.addAttribute("usuarios", usuarioRepository.findAll());
	    return "lista-usuarios";
	}
	
	 @GetMapping("/usuarios/editar/{id}")
	    public String editarUsuario(@PathVariable Long id, Model model) {
	        Usuario usuario = usuarioRepository.findById(id).orElse(null);
	        if (usuario == null) {
	            return "redirect:/lista-usuarios";
	        }
	        model.addAttribute("usuario", usuario);
	        return "formulario-usuario";
	    }
	
	@GetMapping("/detalhes-usuario/{id}")
	public String detalhesUsuario(@PathVariable Long id, Model model) {
	    Usuario usuario = usuarioRepository.findById(id).orElse(null);

	    if (usuario == null) {
	        return "redirect:/lista-usuarios"; // redireciona caso o usuário não exista
	    }

	    model.addAttribute("usuario", usuario);
	    return "detalhes-usuario";
	}
    
	
    @GetMapping("/usuarios/deletar/{id}")
    public String deletarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return "redirect:/lista-usuarios";
    }

    @PostMapping("/formulario-usuario")
    public String processarFormulario(@ModelAttribute("usuario") @Valid Usuario usuario,
                                      BindingResult result,
                                      RedirectAttributes redirectAttributes) { 

       
        if (result.hasErrors()) {
            return "formulario-usuario";
        }

        if ("ErroSalvamento".equalsIgnoreCase(usuario.getNome())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao tentar salvar o usuário. Nome 'ErroSalvamento' não permitido.");
            redirectAttributes.addFlashAttribute("usuario", usuario);
            return "redirect:/formulario-usuario"; 
        }

       
        System.out.println("Dados válidos recebidos e processados:");
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("Email: " + usuario.getEmail());

        usuarioRepository.save(usuario); // Salva no banco
        
        redirectAttributes.addFlashAttribute("confirmacao", "Usuário " + usuario.getNome() + " cadastrado com sucesso!");
        redirectAttributes.addFlashAttribute("usuarioProcessado", usuario);

        return "redirect:/resultado-formulario"; 
    }
    
    



//	@PostMapping("/formulario-usuario") 
//	public ModelAndView processarFormulario(@Valid Usuario usuario, BindingResult result) {
//      
//		if (result.hasErrors()) {
//			ModelAndView mv = new ModelAndView("formulario-usuario"); 
//			mv.addObject("message", "Erro ao processar formulário.");
//			mv.addObject("usuario", usuario);
//			
//			return mv;
//	    }
//
//		ModelAndView mv = new ModelAndView("resultado-formulario");
//        mv.addObject("usuarioProcessado", usuario);
//        mv.addObject("confirmacao", "Dados do usuário recebidos com sucesso!");
//        return mv;
//        
//    }
	
}