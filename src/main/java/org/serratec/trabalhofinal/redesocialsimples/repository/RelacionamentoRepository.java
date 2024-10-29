package org.serratec.trabalhofinal.redesocialsimples.repository;

import org.serratec.trabalhofinal.redesocialsimples.entity.Relacionamento;
import org.serratec.trabalhofinal.redesocialsimples.entity.RelacionamentoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RelacionamentoRepository extends JpaRepository<Relacionamento, RelacionamentoPK>{
    @Query(value = "SELECT COUNT(*) > 0 FROM relacionamento WHERE seguidor_id = :seguidorId AND seguido_id = :seguidoId", nativeQuery = true)
    boolean existsBySeguidorIdAndSeguidoId(@Param("seguidorId") Long seguidorId, @Param("seguidoId") Long seguidoId);
}
