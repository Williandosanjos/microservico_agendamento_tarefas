package com.backend.agendamentotarefas.infrastructure.security;

import com.backend.agendamentotarefas.business.dto.UsuarioDTO;
import com.backend.agendamentotarefas.infrastructure.client.UsuarioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl {

    // Repositório para acessar dados de usuário no banco de dados
    @Autowired
    private UsuarioClient client;

    public UserDetails loadUserByUsername(String email, String authorizationHeader) {

        UsuarioDTO usuarioDTO = client.buscaUsuarioPormail(email, authorizationHeader);

        return User
                .withUsername(usuarioDTO.getEmail()) // Define o nome de usuário como o e-mail
                .password(usuarioDTO.getSenha()) // Define a senha do usuário
                .build(); // Constrói o objeto UserDetails

    }
}
