package org.serratec.trabalhofinal.redesocialsimples.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.serratec.trabalhofinal.redesocialsimples.dto.PostagemDTO;
import org.serratec.trabalhofinal.redesocialsimples.dto.PostagemInserirDTO;
import org.serratec.trabalhofinal.redesocialsimples.entity.Usuario;
import org.serratec.trabalhofinal.redesocialsimples.service.PostagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/postagens")
public class PostagemController {

	@Autowired
	PostagemService postagemService;

	@GetMapping
	@Operation(summary = "Lista todos as postagens", description = "Mostra a Lista de postagens com o conteudo da postagem, a data de criação e os dados do usuario que postou")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Usuario.class),mediaType = "application/json")},
					description = "Retorna todos as postagens"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
	})
	public ResponseEntity<List<PostagemDTO>> listar() {
		return ResponseEntity.ok(postagemService.findall());
	}

	@GetMapping("/{id}") 
	@Operation(summary = "Lista uma postagem usando seu id", description = "Mostra a Lista de postagens com o conteudo da postagem, a data de criação e os dados do usuario que postou")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna uma postagem"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
	})
	public ResponseEntity<PostagemDTO> buscar(@PathVariable Long id) {
	    Optional<PostagemDTO> postagemOpt = postagemService.buscarPorId(id);

	    if (postagemOpt.isPresent()) {
	        return ResponseEntity.ok(postagemOpt.get());
	    }

	    return ResponseEntity.notFound().build();
	}


	@PostMapping
	@Operation(summary = "Insere uma postagem no sistema", description = "Adiciona uma postagem no sistema")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Postagem adicionado com sucesso"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
	})
	public ResponseEntity<PostagemDTO> inserir(@RequestBody PostagemInserirDTO postagemInserirDTO) throws EntityNotFoundException{
		PostagemDTO postagemDTO = postagemService.adicionar(postagemInserirDTO);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(postagemDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(postagemDTO);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualiza uma postagem do sistema", description = "Atualiza uma postagem do sistema")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Postagem atualizado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro de entrada"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
	})
	public ResponseEntity<PostagemDTO> atualizar(@PathVariable Long id,
			@Valid @RequestBody PostagemInserirDTO postagemInserirDTO) throws RuntimeException {
		PostagemDTO postagemAtualizado = postagemService.atualizarPostagem(id, postagemInserirDTO);

		return ResponseEntity.ok(postagemAtualizado);
	}
	
	@GetMapping("/paginas")
	@Operation(summary = "Lista as postagens por páginas", description = "Mostra a Lista paginada de postagens com o conteudo da postagem, a data de criação e os dados do usuario que postou")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna todos as postagens por páginas"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
	})
	public ResponseEntity<Page<PostagemDTO>> listarPaginado(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 8) Pageable pageable) {
		Page<PostagemDTO> usuario = postagemService.paginacao(pageable);
		return ResponseEntity.ok(usuario);
	}

	@DeleteMapping("/{id}") 
	@Operation(summary = "Deleta uma postagem do sistema", description = "Deleta uma postagem do sistema")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Postagem deletado com sucesso"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
	})
	public ResponseEntity<Void> remover(@PathVariable Long id) {
	    Optional<PostagemDTO> postagemOpt = postagemService.buscarPorId(id);

	    if (postagemOpt.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }

	    postagemService.deletar(id);
	    return ResponseEntity.noContent().build();
	}



}