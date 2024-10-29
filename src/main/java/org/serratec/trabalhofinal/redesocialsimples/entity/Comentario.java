package org.serratec.trabalhofinal.redesocialsimples.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "comentario")
public class Comentario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_comentario")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_postagem")
	@JsonBackReference
	private Postagem postagem;

	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;

	@NotBlank(message = "Preencha com o conteúdo")
	@Column(name = "comentario", nullable = false)
	private String conteudo;

	@NotNull
	@Column(name = "data_criacao", nullable = false)
	private LocalDate dateCriacao;

	public Comentario() {
	}

	public Comentario(Long id, String conteudo, LocalDate dateCriacao, Postagem postagem) {
		super();
		this.id = id;
		this.conteudo = conteudo;
		this.dateCriacao = dateCriacao;
		this.postagem = postagem;
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

	public LocalDate getDateCriacao() {
		return dateCriacao;
	}

	public void setDateCriacao(LocalDate dateCriacao) {
		this.dateCriacao = dateCriacao;
	}

	public Postagem getPostagem() {
		return postagem;
	}

	public void setPostagem(Postagem postagem) {
		this.postagem = postagem;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
