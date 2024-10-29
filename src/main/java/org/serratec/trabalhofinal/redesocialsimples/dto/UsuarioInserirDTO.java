package org.serratec.trabalhofinal.redesocialsimples.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioInserirDTO {

	@NotBlank(message = "Preencha o nome")
	private String nome;
	
	@NotBlank(message = "Preencha com o sobrenome")
	private String sobrenome;
	
	@NotBlank(message = "Preencha com o E-mail")
	private String email;
	
	@NotNull
	private LocalDate dataNascimento;
	
	@NotBlank(message = "Preencha com a senha")
	private String senha;
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
