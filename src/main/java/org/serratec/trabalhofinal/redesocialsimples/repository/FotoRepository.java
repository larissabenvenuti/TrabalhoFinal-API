package org.serratec.trabalhofinal.redesocialsimples.repository;

import java.util.Optional;

import org.serratec.trabalhofinal.redesocialsimples.entity.Foto;
import org.serratec.trabalhofinal.redesocialsimples.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {

	Optional<Foto> findByUsuario(Usuario usuario);
	
}