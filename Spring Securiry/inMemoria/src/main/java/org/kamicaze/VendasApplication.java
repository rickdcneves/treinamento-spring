package org.kamicaze;
import org.kamicaze.domain.entity.Cliente;
import org.kamicaze.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner run (@Autowired Clientes clientes){
        return args -> {
            Cliente cliente = new Cliente();
            cliente.setNome("Rico");
            clientes.save(cliente);
        };
    }
    public static void main(String args[]){
        SpringApplication.run(VendasApplication.class,args);
    }
}
