package org.serratec.trabalhofinal.redesocialsimples.service;

import java.util.List;
import java.util.Optional;

import org.serratec.trabalhofinal.redesocialsimples.dto.PostagemDTO;
import org.serratec.trabalhofinal.redesocialsimples.dto.PostagemInserirDTO;
import org.serratec.trabalhofinal.redesocialsimples.entity.Postagem;
import org.serratec.trabalhofinal.redesocialsimples.entity.Usuario;
import org.serratec.trabalhofinal.redesocialsimples.repository.PostagemRepository;
import org.serratec.trabalhofinal.redesocialsimples.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PostagemService {

	@Autowired
	private PostagemRepository postagemRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<PostagemDTO> findall() {
		List<Postagem> postagens = postagemRepository.findAll();
		List<PostagemDTO> postagensDTO = postagens.stream().map(PostagemDTO::new).toList();
		return postagensDTO;
	}

	public Optional<PostagemDTO> buscarPorId(Long id) {
	    return postagemRepository.findById(id).map(PostagemDTO::new);
	}
	
	public Page<PostagemDTO> paginacao(Pageable pageable) {
		Page<Postagem> postagens = postagemRepository.findAll(pageable);
		List<PostagemDTO> postagemDTO = postagens.stream().map(PostagemDTO::new).toList();
		return new PageImpl<>(postagemDTO, pageable, postagens.getTotalElements());
	}

	@Transactional
	public PostagemDTO adicionar(PostagemInserirDTO postagemInserirDTO) throws EntityNotFoundException{
	    Optional<Usuario> usuarioOpt = usuarioRepository.findById(postagemInserirDTO.getUsuario().getId());
	    
	    if (usuarioOpt.isPresent()) {
	        Usuario usuario = usuarioOpt.get();  

	        Postagem postagem = new Postagem();
	        postagem.setUsuario(usuario); 
	        postagem.setConteudo(postagemInserirDTO.getConteudo());
	        postagem.setDataCriacao(postagemInserirDTO.getDatacriacao());

	        postagem = postagemRepository.save(postagem);

	        return new PostagemDTO(postagem);
	    } else {
	        throw new EntityNotFoundException("Usuário não encontrado");
	    }
	}
	
	@Transactional
	public PostagemDTO atualizarPostagem(Long id, PostagemInserirDTO postagemInserirDTO) throws RuntimeException {
	    Postagem postagem = postagemRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));

	    postagem.setConteudo(postagemInserirDTO.getConteudo());
	    postagem.setDataCriacao(postagemInserirDTO.getDatacriacao());

	    Postagem postagemAtualizada = postagemRepository.save(postagem);

	    return new PostagemDTO(postagemAtualizada);
	}


	public void deletar(Long id) {
		postagemRepository.deleteById(id);
	}
}