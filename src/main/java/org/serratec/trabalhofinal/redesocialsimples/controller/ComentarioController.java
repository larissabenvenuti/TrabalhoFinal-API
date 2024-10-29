package org.serratec.trabalhofinal.redesocialsimples.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.serratec.trabalhofinal.redesocialsimples.dto.ComentarioDTO;
import org.serratec.trabalhofinal.redesocialsimples.dto.ComentarioInserirDTO;
import org.serratec.trabalhofinal.redesocialsimples.entity.Usuario;
import org.serratec.trabalhofinal.redesocialsimples.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {
	
	@Autowired
	ComentarioService comentarioService;
	
	@GetMapping
	@Operation(summary = "Lista todos os comentários dos usuarios", description = "Mostra a Lista de comentários feitos pelos usuarios")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Usuario.class),mediaType = "application/json")},
					description = "Retorna todos os comentários de um usuario"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
	})
	public ResponseEntity<List<ComentarioDTO>> listar() {
		return ResponseEntity.ok(comentarioService.findall());
	}
	
	@GetMapping("/{id}") 
	@Operation(summary = "Lista um comentário usando seu id", description = "Mostra a Lista de comentários feitos pelo usuario")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna um comentário"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
	})
	public ResponseEntity<ComentarioDTO> buscar(@PathVariable Long id) {
	    Optional<ComentarioDTO> comentarioOpt = comentarioService.buscarPorId(id);

	    if (comentarioOpt.isPresent()) {
	        return ResponseEntity.ok(comentarioOpt.get());
	    }

	    return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/paginas")
	@Operation(summary = "Lista todos os comentários dos usuarios por páginas", description = "Mostra uma lista paginada de comentários feitos pelos usuarios")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna todos os comentários por páginas"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
	})
	public ResponseEntity<Page<ComentarioDTO>> listarPaginado(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 8) Pageable pageable) {
		Page<ComentarioDTO> usuario = comentarioService.paginacao(pageable);
		return ResponseEntity.ok(usuario);
	}

	@PostMapping
	@Operation(summary = "Insere um comentário no sistema", description = "Adiciona um comentário no sistema")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Comentário adicionado com sucesso"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
	})
	public ResponseEntity<ComentarioDTO> inserir(@RequestBody ComentarioInserirDTO comentarioInserirDTO) throws AccessDeniedException, EntityNotFoundException {
	        ComentarioDTO comentarioDTO = comentarioService.adicionar(comentarioInserirDTO);
	        URI uri = ServletUriComponentsBuilder
	                .fromCurrentRequest()
	                .path("/{id}")
	                .buildAndExpand(comentarioDTO.getId())
	                .toUri();
	        return ResponseEntity.created(uri).body(comentarioDTO);
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Atualiza um comentário do sistema", description = "Atualiza um comentário do sistema")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Comentário atualizado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro de entrada"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
	})
	public ResponseEntity<ComentarioDTO> atualizar(@PathVariable Long id,
			@Valid @RequestBody ComentarioInserirDTO comentarioInserirDTO) throws RuntimeException {
		ComentarioDTO comentarioAtualizado = comentarioService.atualizarComentario(id, comentarioInserirDTO);

		return ResponseEntity.ok(comentarioAtualizado);
	}
	
	@DeleteMapping("/{id}") 
	@Operation(summary = "Deleta um comentário do sistema", description = "Deleta um comentário do sistema")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Comentário deletado com sucesso"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
	})
	public ResponseEntity<Void> remover(@PathVariable Long id) {
	    Optional<ComentarioDTO> comentarioOpt = comentarioService.buscarPorId(id);

	    if (comentarioOpt.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }

	    comentarioService.deletar(id);
	    return ResponseEntity.noContent().build();
	}
}
