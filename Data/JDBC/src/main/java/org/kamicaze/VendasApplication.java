package org.kamicaze;
import org.kamicaze.domain.entity.Cliente;
import org.kamicaze.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@SpringBootApplication
@RestController
public class VendasApplication {

    @Bean
    public CommandLineRunner init (@Autowired Clientes clientes){
        return args -> {
            Cliente cliente = new Cliente("Ricardo Neves");
            clientes.salvar(cliente);

            Cliente cliente1 = new Cliente("Ricardo Jorge");
            clientes.salvar(cliente1);

            List<Cliente> todosClientes = clientes.getAll();
            todosClientes.forEach(System.out::println);

            todosClientes.forEach(c->{
                c.setNome(c.getNome()+" Actualizado");
                clientes.actualizar(c);
            });

            todosClientes = clientes.getAll();
            todosClientes.forEach(System.out::println);

            clientes.buscarNome("Jo").forEach(System.out::println);

            System.out.println("Limpeza");
            todosClientes.forEach(c->{
                clientes.apagar(c.getId());
            });
            todosClientes = clientes.getAll();
            todosClientes.forEach(System.out::println);
        };
    }
    public static void main(String args[]){
        SpringApplication.run(VendasApplication.class,args);
    }
}
