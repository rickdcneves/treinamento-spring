package org.kamicaze.rest.controller;

import org.kamicaze.domain.entity.Cliente;
import org.kamicaze.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("")
public class ClienteController {
    @Autowired
    private Clientes clientes;
    @GetMapping("/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity getClienteById(@PathVariable("id") Integer id){

        Optional<Cliente> cliente = clientes.findById(id);
        if(cliente.isPresent()){
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/clientes/save")
    @ResponseBody
    public ResponseEntity save (@RequestBody Cliente cliente){
       return  ResponseEntity.ok(clientes.save(cliente));
    }

    @DeleteMapping("api/clientes/delete/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable("id") Integer id){
        Optional<Cliente> cliente = clientes.findById(id);
        if(cliente.isPresent()){
            clientes.delete(cliente.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("api/clientes/update/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable Integer id, @RequestBody Cliente cliente){
        return clientes
                .findById(id)
                .map(clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clientes.save(cliente);
                    return ResponseEntity.noContent().build();
                }).orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping("api/clientes")
    @ResponseBody
    public ResponseEntity getAll(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        List <Cliente> lista = clientes.findAll(example);
        return ResponseEntity.ok(lista);

    }
}
