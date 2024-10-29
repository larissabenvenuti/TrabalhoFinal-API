package org.serratec.trabalhofinal.redesocialsimples.repository;

import org.serratec.trabalhofinal.redesocialsimples.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long>{

}
