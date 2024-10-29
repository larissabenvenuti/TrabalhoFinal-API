package org.serratec.trabalhofinal.redesocialsimples.repository;

import java.util.List;

import org.serratec.trabalhofinal.redesocialsimples.dto.UsuarioPostagemDTO;
import org.serratec.trabalhofinal.redesocialsimples.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Usuario findByEmail(String email);

    @Query("SELECT new org.serratec.trabalhofinal.redesocialsimples.dto.UsuarioPostagemDTO(u.id, u.nome, u.sobrenome, u.email, p.id, p.conteudo, p.dataCriacao) " +
    	       "FROM Usuario u LEFT JOIN Postagem p ON u.id = p.usuario.id " +
    	       "WHERE u.id = :usuario_id " +
    	       "ORDER BY p.dataCriacao DESC")
    	List<UsuarioPostagemDTO> buscarUsuarioComPostagens(@Param("usuario_id") Long usuarioId);
}