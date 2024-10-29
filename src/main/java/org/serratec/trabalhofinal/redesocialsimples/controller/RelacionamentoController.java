package org.serratec.trabalhofinal.redesocialsimples.controller;

import java.util.List;
import java.util.Optional;

import org.serratec.trabalhofinal.redesocialsimples.entity.Relacionamento;
import org.serratec.trabalhofinal.redesocialsimples.entity.RelacionamentoPK;
import org.serratec.trabalhofinal.redesocialsimples.entity.Usuario;
import org.serratec.trabalhofinal.redesocialsimples.repository.RelacionamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/relacionamentos")
public class RelacionamentoController {

	@Autowired
	RelacionamentoRepository relacionamentoRepository;

	@GetMapping
	@Operation(summary = "Lista os relacionamentos", description = "Mostra os seguidores dos usuarios")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Usuario.class),mediaType = "application/json")},
					description = "Retorna os seguidores do usuario e quem ele segue"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
	})
	public ResponseEntity<List<Relacionamento>> listar() {
		return ResponseEntity.ok(relacionamentoRepository.findAll());
	}

	@GetMapping("/{seguidorId}/{seguidoId}")
	@Operation(summary = "Lista os relacionamentos de um usuario especifico", description = "Mostra um seguidor de um usuario especifico")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna um relacionamentos"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
	})
	public ResponseEntity<Relacionamento> pesquisar(@PathVariable Usuario seguidorId, @PathVariable Usuario seguidoId) {

		RelacionamentoPK relacionamentoPK = new RelacionamentoPK();
		relacionamentoPK.setSeguidor(seguidorId);
		relacionamentoPK.setSeguido(seguidoId);

		Optional<Relacionamento> relacionamentoOpt = relacionamentoRepository.findById(relacionamentoPK);
		if (relacionamentoOpt.isPresent()) {
			Relacionamento relacionamento = relacionamentoOpt.get();
			return ResponseEntity.ok(relacionamento);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@Operation(summary = "Insere um seguidor a um usuario", description = "Adiciona um relacionamento ao sistema")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Relacionamento adicionado com sucesso"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
	})
	@ResponseStatus(HttpStatus.CREATED)
	public Relacionamento inserir(@Valid @RequestBody Relacionamento relacionamento) {
		relacionamento = relacionamentoRepository.save(relacionamento);
		return relacionamento;
	}

	@DeleteMapping("/{seguidorId}/{seguidoId}")
	@Operation(summary = "Deleta um relacionamento do sistema", description = "Deleta um relacionamento do sistema")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Relacionamento deletado com sucesso"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação"),
			@ApiResponse(responseCode = "403", description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Exceção interna do servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
	})
	public ResponseEntity<Void> remover(@PathVariable Usuario seguidorId, @PathVariable Usuario seguidoId) {

	    RelacionamentoPK relacionamentoPK = new RelacionamentoPK();
	    relacionamentoPK.setSeguidor(seguidorId);
	    relacionamentoPK.setSeguido(seguidoId);

	    if (!relacionamentoRepository.existsById(relacionamentoPK)) {
	        return ResponseEntity.notFound().build();
	    }

	    relacionamentoRepository.deleteById(relacionamentoPK);
	    return ResponseEntity.noContent().build();
	}

}
