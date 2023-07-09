package org.kamicaze.service;

import org.kamicaze.domain.entity.Pedido;
import org.kamicaze.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);
    Optional<Pedido> obterPedidoCompleto (Integer id);
}
