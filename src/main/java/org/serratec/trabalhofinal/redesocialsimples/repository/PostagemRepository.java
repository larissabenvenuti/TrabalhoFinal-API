package org.serratec.trabalhofinal.redesocialsimples.repository;

import org.serratec.trabalhofinal.redesocialsimples.entity.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long>{

}
