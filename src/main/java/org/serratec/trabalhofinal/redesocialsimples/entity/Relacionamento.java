package org.serratec.trabalhofinal.redesocialsimples.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "relacionamento")
public class Relacionamento {

	@EmbeddedId
	private RelacionamentoPK id = new RelacionamentoPK();
	
	
	@NotNull
	@Column(name = "data_inicio_seguimento", nullable = false)
	private LocalDate dataInicioSeguimento;

	public Relacionamento() {
	}
	
	public Relacionamento(RelacionamentoPK id, LocalDate dataInicioSeguimento) {
		super();
		this.id = id;
		this.dataInicioSeguimento = dataInicioSeguimento;
	}

	public RelacionamentoPK getId() {
		return id;
	}

	public void setId(RelacionamentoPK id) {
		this.id = id;
	}

	public LocalDate getDataInicioSeguimento() {
		return dataInicioSeguimento;
	}

	public void setDataInicioSeguimento(LocalDate dataInicioSeguimento) {
		this.dataInicioSeguimento = dataInicioSeguimento;
	}
	
}
