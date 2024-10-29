package org.serratec.trabalhofinal.redesocialsimples.service;

import java.io.IOException;
import java.util.Optional;

import org.serratec.trabalhofinal.redesocialsimples.entity.Foto;
import org.serratec.trabalhofinal.redesocialsimples.entity.Usuario;
import org.serratec.trabalhofinal.redesocialsimples.repository.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoService {

	@Autowired
	private FotoRepository fotoRepository;
	
	public Foto inserir(Usuario usuario, MultipartFile file) throws IOException {
		Foto foto = new Foto();
		foto.setNome(file.getName());
		foto.setTipo(file.getContentType());
		foto.setDados(file.getBytes());
		foto.setUsuario(usuario);
		return fotoRepository.save(foto);
	}
	
	@Transactional
	public Foto buscarPorIdUsario(Long id) {
		Usuario usuario = new Usuario();
		usuario.setId(id);
		Optional<Foto> foto = fotoRepository.findByUsuario(usuario);
		if (foto.isEmpty()) {
			return null;
		}
		return foto.get();
	}
	
	
}