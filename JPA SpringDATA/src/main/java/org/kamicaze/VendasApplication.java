package org.kamicaze;
import org.kamicaze.domain.entity.Cliente;
import org.kamicaze.domain.entity.Pedido;
import org.kamicaze.domain.repository.Clientes;
import org.kamicaze.domain.repository.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@SpringBootApplication
@RestController
public class VendasApplication {

    @Bean
    public CommandLineRunner init (@Autowired Clientes clientes, @Autowired Pedidos pedidos){
        return args -> {
            Cliente cliente = new Cliente("Ricardo Neves");
            clientes.save(cliente);

            Cliente cliente1 = new Cliente("Jorge da Costa");
            clientes.save(cliente1);

            //System.out.println("Intert");
            //List<Cliente> todosClientes = clientes.findAll();
            //todosClientes.forEach(System.out::println);

            Pedido p = new Pedido();
            p.setCliente(cliente);
            p.setDataPedido(LocalDate.now());
            p.setTotal(BigDecimal.valueOf(100));
            pedidos.save(p);

            /*Cliente c = clientes.findClientFetchPedidos(cliente.getId());
            System.out.printf(c.toString());
            System.out.println(c.getPedido());
            */

            pedidos.findByCliente(cliente).forEach(System.out::println);
/*
            clientes.findAll().forEach(c->{
                c.setNome(c.getNome()+" Actualizado");
                clientes.save(c);
            });
            System.out.println("Update");
            todosClientes = clientes.findAll();
            todosClientes.forEach(System.out::println);

            System.out.println("Seacrh");
            //clientes.findByNomeLike("%Ne%").forEach(System.out::println);
            List <Cliente> result = clientes.encontrarNome("%Ne%");
            result.forEach(System.out::println);
            //clientes.findByNomeLike("%Ne%").forEach(System.out::println);

            System.out.println("Limpeza");
            clientes.findAll().forEach(c->{
                clientes.delete(c);
            });
            todosClientes = clientes.findAll();
            todosClientes.forEach(System.out::println);*/
        };
    }
    public static void main(String args[]){
        SpringApplication.run(VendasApplication.class,args);
    }
}
