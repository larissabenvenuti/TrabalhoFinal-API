package org.serratec.trabalhofinal.redesocialsimples.service;

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
import org.serratec.trabalhofinal.redesocialsimples.repository.FotoRepository;
import org.serratec.trabalhofinal.redesocialsimples.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	FotoRepository fotoRepository;
	
	@Autowired
	private FotoService fotoService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public List<UsuarioDTO> findAll() {
	    List<Usuario> usuarios = usuarioRepository.findAll();
	    return usuarios.stream()
	            .map(this::adicionarImagemUri) 
	            .toList();
	}

	
	public Page<UsuarioDTO> paginacao(Pageable pageable) {
		Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
		List<UsuarioDTO> usuarioDTO = usuarios.stream().map(UsuarioDTO::new).toList();
		return new PageImpl<>(usuarioDTO, pageable, usuarios.getTotalElements());
	}
	
	public Optional<Usuario> buscar(Long id) {
		return usuarioRepository.findById(id);
	}
	
	public List<UsuarioPostagemDTO> buscarPostagensPorUsuario(Long userId) {
        return usuarioRepository.buscarUsuarioComPostagens(userId);
    }
	
	public UsuarioDTO adicionarImagemUri(Usuario usuario) {
	    URI uri = ServletUriComponentsBuilder
	            .fromCurrentContextPath()
	            .path("/usuarios/{id}/foto") 
	            .buildAndExpand(usuario.getId())
	            .toUri();
	    UsuarioDTO dto = new UsuarioDTO();
	    dto.setId(usuario.getId()); 
	    dto.setNome(usuario.getNome());
	    dto.setSobrenome(usuario.getSobrenome());
	    dto.setDataNascimento(usuario.getDataNascimento());
	    dto.setEmail(usuario.getEmail());
	    dto.setUrl(uri.toString());
	    return dto;
	}
	
	public UsuarioDTO inserir(UsuarioInserirDTO usuarioInserirDTO, MultipartFile file) throws IOException {
	    Usuario usuarioExistente = usuarioRepository.findByEmail(usuarioInserirDTO.getEmail());
	    if (usuarioExistente != null) {
	        throw new EmailException("Já existe um usuário cadastrado com este email.");
	    }

	    Usuario usuario = new Usuario();
	    usuario.setNome(usuarioInserirDTO.getNome());
	    usuario.setSobrenome(usuarioInserirDTO.getSobrenome());
	    usuario.setEmail(usuarioInserirDTO.getEmail());
	    usuario.setDataNascimento(usuarioInserirDTO.getDataNascimento());
	    usuario.setSenha(encoder.encode(usuarioInserirDTO.getSenha()));

	    usuario = usuarioRepository.save(usuario);

	    if (file != null && !file.isEmpty()) {
	        fotoService.inserir(usuario, file);
	    }

	    return adicionarImagemUri(usuario);
	}

	
	@Transactional
	public UsuarioDTO atualizarUsuario(Long id, UsuarioInserirDTO usuarioInserirDTO) throws RuntimeException {

        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNome(usuarioInserirDTO.getNome());
        usuario.setSobrenome(usuarioInserirDTO.getSobrenome());
        usuario.setEmail(usuarioInserirDTO.getEmail());
        usuario.setSenha(encoder.encode(usuarioInserirDTO.getSenha()));
        usuario.setDataNascimento(usuarioInserirDTO.getDataNascimento());

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuarioAtualizado.getId());
        usuarioDTO.setNome(usuarioAtualizado.getNome());
        usuarioDTO.setSobrenome(usuarioAtualizado.getSobrenome());
        usuarioDTO.setEmail(usuarioAtualizado.getEmail());
        usuarioDTO.setDataNascimento(usuarioAtualizado.getDataNascimento());

        return usuarioDTO;
    }
	
	@Transactional
	public void deletar(Long id) {
	    Usuario usuario = usuarioRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

	    Optional<Foto> fotoOpt = fotoRepository.findByUsuario(usuario);
	    fotoOpt.ifPresent(foto -> fotoRepository.delete(foto)); 

	    usuarioRepository.deleteById(id);
	}
	
}