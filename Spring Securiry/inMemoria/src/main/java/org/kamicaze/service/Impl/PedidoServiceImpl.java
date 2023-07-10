package org.kamicaze.service.Impl;

import lombok.RequiredArgsConstructor;
import org.kamicaze.domain.emum.StatusPedido;
import org.kamicaze.domain.entity.Cliente;
import org.kamicaze.domain.entity.ItemPedido;
import org.kamicaze.domain.entity.Pedido;
import org.kamicaze.domain.entity.Produto;
import org.kamicaze.domain.repository.Clientes;
import org.kamicaze.domain.repository.ItemsPedido;
import org.kamicaze.domain.repository.Pedidos;
import org.kamicaze.domain.repository.Produtos;
import org.kamicaze.exception.PedidoNaoEncontradoException;
import org.kamicaze.exception.RegraNegocioException;
import org.kamicaze.rest.dto.ItemPedidoDTO;
import org.kamicaze.rest.dto.PedidoDTO;
import org.kamicaze.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItemsPedido itemPedidoRepository;

    @Override
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(()-> new RegraNegocioException("Codigo de Cliente Invalido"));


        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);
        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
        repository.save(pedido);
        itemPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    public void actulizaStatus(Integer id, StatusPedido status) {
        repository.findById(id)
                .map(pedido -> {
                        pedido.setStatus(status);
                        return repository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não foi possivel não temos items");
        }
        return items
                .stream()
                .map(dto ->{
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto).orElseThrow(() -> new RegraNegocioException("Codigo de produto Invalido: "+idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);

                    return itemPedido;
                }).collect(Collectors.toList());
    }


}
