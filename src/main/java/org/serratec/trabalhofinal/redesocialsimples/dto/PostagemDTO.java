package org.serratec.trabalhofinal.redesocialsimples.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.serratec.trabalhofinal.redesocialsimples.entity.Postagem;

public class PostagemDTO {

    private Long id;
    private UsuarioDTO usuario;
    private String conteudo;
    private LocalDate dataCriacao;
    private List<ComentarioDTO> comentarios;
      
    public PostagemDTO() {
    }

    public PostagemDTO(Long id, String conteudo, LocalDate dataCriacao, UsuarioDTO usuario, List<ComentarioDTO> comentarios) {
        this.id = id;
        this.conteudo = conteudo;
        this.dataCriacao = dataCriacao;
        this.usuario = usuario;
        this.comentarios = comentarios != null ? comentarios : new ArrayList<>(); 
    }

    public PostagemDTO(Postagem postagem) {
        this.id = postagem.getId();
        this.conteudo = postagem.getConteudo();
        this.dataCriacao = postagem.getDataCriacao();
        this.usuario = new UsuarioDTO(postagem.getUsuario());

        this.comentarios = postagem.getComentario() != null ? 
            postagem.getComentario().stream()
            .map(ComentarioDTO::new)
            .toList() : new ArrayList<>();
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

    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
}
