package com.banana.bananawhatsapp.config;

import org.springframework.context.annotation.*;

@Configuration
@Import({ReposConfig.class, ServicesConfig.class, ControllersConfig.class})
@ComponentScan(basePackages = {"com.banana.bananawhatsapp.persistencia", "com.banana.bananawhatsapp.servicios"})
@PropertySource("classpath:application.properties")
public class SpringConfig {

}

