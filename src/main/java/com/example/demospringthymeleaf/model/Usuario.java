package com.example.demospringthymeleaf.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @NotEmpty(message = "O nome é obrigatório")
    private String nome;
    
    @NotBlank(message = "O e-mail é obrigatório")
    @Email (message = "Formato de e-mail inválido")
    private String email;

    public Usuario() {}

    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }
 
    @Override
    public String toString() {
        return "Usuario{" +
               "nome='" + nome + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
    
}