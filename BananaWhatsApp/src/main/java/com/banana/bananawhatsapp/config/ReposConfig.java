package com.banana.bananawhatsapp.config;

import com.banana.bananawhatsapp.persistencia.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class ReposConfig {

    @Value("${db_url}")
    String connUrl;

    @Bean
    public DBConnector createDBConnector() {
        return new DBConnector();
    }

    @Bean
    //@Profile("default")
    public IUsuarioRepository createIUsuarioRepository() {
        UsuarioRepository repo = new UsuarioRepository();
        System.out.println("connUrl: " + connUrl);
        repo.setDb_url(connUrl);
        return repo;
    }

       @Bean
    //@Profile("default")
    public IMensajeRepository createIMensajeRepository() {
        MensajeRepository repo = new MensajeRepository();
        System.out.println("connUrl: " + connUrl);
        repo.setDb_url(connUrl);
        return repo;
    }

}
