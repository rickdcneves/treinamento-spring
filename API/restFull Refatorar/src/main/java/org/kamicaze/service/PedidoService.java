package org.kamicaze.service;

import org.kamicaze.domain.entity.Pedido;
import org.kamicaze.rest.dto.PedidoDTO;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

}
