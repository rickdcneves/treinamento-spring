package org.kamicaze;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinhaConfiguration {
    @Bean(name = "nome")
    public String nomeAPP(){
        return "Sistema de Vendas Training";
    }
}
