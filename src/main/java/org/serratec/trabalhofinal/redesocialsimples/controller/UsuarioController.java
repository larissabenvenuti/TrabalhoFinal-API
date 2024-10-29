package org.serratec.trabalhofinal.redesocialsimples.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.serratec.trabalhofinal.redesocialsimples.dto.UsuarioDTO;
import org.serratec.trabalhofinal.redesocialsimples.dto.UsuarioInserirDTO;
import org.serratec.trabalhofinal.redesocialsimples.dto.UsuarioPostagemDTO;
import org.serratec.trabalhofinal.redesocialsimples.entity.Foto;
import org.serratec.trabalhofinal.redesocialsimples.entity.Usuario;
import org.serratec.trabalhofinal.redesocialsimples.exception.EmailException;
import org.serratec.trabalhofinal.redesocialsimples.service.FotoService;
import org.serratec.trabalhofinal.redesocialsimples.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	FotoService fotoService;

	@GetMapping
	@Operation(summary = "Lista todos os usuarios", description = "Mostra a Lista de usuarios com nome, sobrenome, data de nascimento e email")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = Usuario.class), mediaType = "application/json") }, description = "Retorna todos os Usuarios"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	public ResponseEntity<List<UsuarioDTO>> listar() {
		UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("Login do usuario: " + details.getUsername());
		return ResponseEntity.ok(usuarioService.findAll());
	}

	@GetMapping("/{id}")
	@Operation(summary = "Lista um usuario usando seu id", description = "Mostra a Lista de usuarios com nome, sobrenome, data de nascimento e email")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retorna um usuario"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long id) {
		Optional<Usuario> usuarioOpt = usuarioService.buscar(id);
		if (usuarioOpt.isPresent()) {
			UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioOpt.get());
			return ResponseEntity.ok(usuarioDTO);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/paginas")
	@Operation(summary = "Lista os usuarios por páginas", description = "Mostra uma lista paginada de um usuario com nome, o sobrenome, a data de nascimento e o email")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retorna todos os usuarios por páginas"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	public ResponseEntity<Page<UsuarioDTO>> listarPaginado(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 8) Pageable pageable) {
		Page<UsuarioDTO> usuario = usuarioService.paginacao(pageable);
		return ResponseEntity.ok(usuario);
	}

	@GetMapping("/{id}/postagens")
	@Operation(summary = "Lista as postagens de um usuario usando seu id", description = "Mostra a Lista de postagens de usuarios com nome, sobrenome, data de nascimento, email, conteudo e data da postagem")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna todas as postagens de um usuario"),
			@ApiResponse(responseCode = "400", description = "Erro de entrada"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	public ResponseEntity<List<UsuarioPostagemDTO>> getPostagensPorUsuario(@PathVariable("id") Long userId) {
		List<UsuarioPostagemDTO> postagens = usuarioService.buscarPostagensPorUsuario(userId);
		if (postagens.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(postagens);
	}

	@GetMapping("/{id}/foto")
	@Operation(summary = "Lista a foto de um usuario usando seu id", description = "Mostra a Foto do usuario")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna todas as postagens de um usuario"),
			@ApiResponse(responseCode = "400", description = "Erro de entrada"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	public ResponseEntity<byte[]> buscarFoto(@PathVariable Long id) {
		Foto foto = fotoService.buscarPorIdUsario(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, foto.getTipo());
		headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(foto.getDados().length));
		return new ResponseEntity<>(foto.getDados(), headers, HttpStatus.OK);
	}

	@PostMapping
	@Operation(summary = "Insere um usuario no sistema", description = "Adiciona um usuario no sistema")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Usuario adicionado com sucesso"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	public ResponseEntity<UsuarioDTO> inserir(@Valid @RequestPart UsuarioInserirDTO usuarioInserirDTO,
			@RequestPart(required = false) MultipartFile file) throws EmailException, IOException {
		UsuarioDTO usuarioDTO = usuarioService.inserir(usuarioInserirDTO, file);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuarioDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(usuarioDTO);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualiza um usuario do sistema", description = "Atualiza um usuario do sistema")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Usuario atualizado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro de entrada"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id,
			@Valid @RequestBody UsuarioInserirDTO usuarioInserirDTO) {
		UsuarioDTO usuarioAtualizado = usuarioService.atualizarUsuario(id, usuarioInserirDTO);

		return ResponseEntity.ok(usuarioAtualizado);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deleta um usuario do sistema", description = "Deleta um usuario do sistema")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Usuario deletado com sucesso"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		Optional<Usuario> usuario = usuarioService.buscar(id);

		if (usuario.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		usuarioService.deletar(id);
		return ResponseEntity.noContent().build();
	}

}