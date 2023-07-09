package org.kamicaze.rest.controller;


import javafx.scene.input.DataFormat;
import org.kamicaze.domain.entity.ItemPedido;
import org.kamicaze.domain.entity.Pedido;
import org.kamicaze.exception.RegraNegocioException;
import org.kamicaze.rest.dto.InformacaoItemPedidoDTO;
import org.kamicaze.rest.dto.InformacaoPedidoDTO;
import org.kamicaze.rest.dto.PedidoDTO;
import org.kamicaze.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    private PedidoService service;
    public PedidoController(PedidoService service){
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody PedidoDTO dto){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacaoPedidoDTO getById(@PathVariable Integer id){
        return service.obterPedidoCompleto(id)
                .map(p ->converter(p))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido Não Encontrado"));
    }

    private InformacaoPedidoDTO converter(Pedido pedido){
        return InformacaoPedidoDTO.builder()
                .nomeCliente(pedido.getCliente().getNome())
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(converter(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens){
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }
        return itens.stream().map(
                item ->
                    InformacaoItemPedidoDTO.builder().descricaoProduto(item.getProduto().getDescricao())
                            .precoUnitario(item.getProduto().getPreco())
                            .quantidade(item.getQuantidade())
                            .build()
            ).collect(Collectors.toList());
    }
}
