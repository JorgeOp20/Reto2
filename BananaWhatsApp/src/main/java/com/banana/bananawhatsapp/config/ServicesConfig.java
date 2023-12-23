package com.banana.bananawhatsapp.config;

import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import com.banana.bananawhatsapp.servicios.IServicioUsuarios;
import com.banana.bananawhatsapp.servicios.ServicioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfig {

    @Autowired
    IUsuarioRepository repoUsuario;

    @Bean
    public IServicioUsuarios getServicioUsuario() {
        ServicioUsuarios servUsu = new ServicioUsuarios();
        servUsu.setRepoUsuario(repoUsuario);
        return servUsu;
    }
}
