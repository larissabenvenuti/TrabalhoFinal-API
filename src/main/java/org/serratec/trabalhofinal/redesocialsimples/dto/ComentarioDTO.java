package org.serratec.trabalhofinal.redesocialsimples.dto;

import java.time.LocalDate;

import org.serratec.trabalhofinal.redesocialsimples.entity.Comentario;

public class ComentarioDTO {

    private Long id;
    private String conteudo;
    private LocalDate dataCriacao;
    private Long usuarioId;

    public ComentarioDTO(Long id, String conteudo, LocalDate dataCriacao, Long usuarioId) {
        this.id = id;
        this.conteudo = conteudo;
        this.dataCriacao = dataCriacao;
        this.usuarioId = usuarioId;
    }

    public ComentarioDTO(Comentario comentario) {
        this.id = comentario.getId();
        this.conteudo = comentario.getConteudo();
        this.dataCriacao = comentario.getDateCriacao();
        this.usuarioId = comentario.getUsuario().getId(); 
    }

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
    
}
