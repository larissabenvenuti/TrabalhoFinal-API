package org.serratec.trabalhofinal.redesocialsimples.dto;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PostagemInserirDTO {

    private UsuarioDTO usuario; 
    
	@NotBlank(message = "Adicione um conteúdo")
    private String conteudo;
	
	@NotNull
    private LocalDate datacriacao;
	
	
	 private Set<ComentarioDTO> comentarios;

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDate getDatacriacao() {
        return datacriacao;
    }

    public void setDatacriacao(LocalDate datacriacao) {
        this.datacriacao = datacriacao;
    }

    public Set<ComentarioDTO> getComentarios() {
        return comentarios;
    }

    public void setComentarios(Set<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }
}
