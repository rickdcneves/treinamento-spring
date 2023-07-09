package org.kamicaze.service;

import org.kamicaze.domain.emum.StatusPedido;
import org.kamicaze.domain.entity.Pedido;
import org.kamicaze.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);
    Optional<Pedido> obterPedidoCompleto (Integer id);

    void actulizaStatus(Integer id, StatusPedido status);
}
