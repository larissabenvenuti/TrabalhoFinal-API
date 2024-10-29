package org.serratec.trabalhofinal.redesocialsimples.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ComentarioInserirDTO {

	@NotBlank(message = "Preencha com o conteúdo")
	private String conteudo;

	@NotNull
	private LocalDate dataCriacao;


	private Long postagemId;


	private Long usuarioId;

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

	public Long getPostagemId() {
		return postagemId;
	}

	public void setPostagemId(Long postagemId) {
		this.postagemId = postagemId;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

}
