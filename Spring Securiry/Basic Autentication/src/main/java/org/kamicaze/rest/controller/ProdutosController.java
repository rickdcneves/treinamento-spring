package org.kamicaze.rest.controller;

import org.kamicaze.domain.entity.Produto;
import org.kamicaze.domain.repository.Produtos;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/produtos")
public class ProdutosController {

    private Produtos produtos;

    public ProdutosController(Produtos produtos){
        this.produtos = produtos;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save(@RequestBody Produto produto){
        return produtos.save(produto);
    }

    @PutMapping("{id}")
    public Produto update(@PathVariable Integer id, @RequestBody Produto produto){
        return produtos.findById(id)
                .map(p ->{
                    produto.setId(p.getId());
                    produtos.save(produto);
                    return produto;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        produtos.findById(id)
                .map(p ->{
                    produtos.delete(p);
                    return p;
                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto Não eocntrado"));
    }

    @GetMapping
    public List<Produto> getAll(){
        return produtos.findAll();
    }

}
