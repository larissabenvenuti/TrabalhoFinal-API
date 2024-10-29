package org.serratec.trabalhofinal.redesocialsimples.dto;

import java.time.LocalDate;

public class UsuarioPostagemDTO {
	private Long usuarioId;
	private String nome;
	private String sobrenome;
	private String email;
	private Long postagemId;
	private String conteudo;
	private LocalDate dataCriacao;

	public UsuarioPostagemDTO(Long usuarioId, String nome, String sobrenome, String email, Long postagemId,
			String conteudo, LocalDate dataCriacao) {
		this.usuarioId = usuarioId;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.email = email;
		this.postagemId = postagemId;
		this.conteudo = conteudo;
		this.dataCriacao = dataCriacao;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

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

	public Long getPostagemId() {
		return postagemId;
	}

	public void setPostagemId(Long postagemId) {
		this.postagemId = postagemId;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

}