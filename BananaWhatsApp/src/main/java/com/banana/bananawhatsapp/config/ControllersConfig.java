package com.banana.bananawhatsapp.config;

import com.banana.bananawhatsapp.controladores.ControladorUsuarios;
import com.banana.bananawhatsapp.servicios.IServicioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class ControllersConfig {

    @Autowired
    IServicioUsuarios servUsers;

     @Bean
    //@Profile("default")
    public ControladorUsuarios createControllerUser() {
        ControladorUsuarios controllerUser = new ControladorUsuarios();
        controllerUser.setServicioUsuarios(servUsers);
        return controllerUser;
    }
}
