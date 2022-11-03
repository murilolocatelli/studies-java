package com.example.service;

import com.example.model.Ordem;
import com.example.model.Usuario;
import com.example.repository.OrdemRepository;
import com.example.repository.UsuarioRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.SecurityContext;


@ApplicationScoped
public class OrdemService {

    @Inject
    OrdemRepository ordemRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    public void inserir(SecurityContext securityContext, Ordem ordem) {
        Optional<Usuario> usuarioOptional =
            this.usuarioRepository.findByIdOptional(ordem.getUserId());

        Usuario usuario = usuarioOptional.orElseThrow();

        if (!usuario.getUsername().equals(securityContext.getUserPrincipal().getName())) {
            throw new RuntimeException("O usuário logado é diferente do userId");
        }

        ordem.setData(LocalDate.now());
        ordem.setStatus("ENVIADA");

        this.ordemRepository.persist(ordem);
    }

    public List<Ordem> listar() {
        return this.ordemRepository.listAll();
    }

}
