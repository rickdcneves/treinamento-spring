package org.kamicaze.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/*{
    "cliente": 1,
    "total": 1000,
    "items": [
        {
            "produto": 1,
            "quantidade": 10
        }
    ]
}*/

@Getter
@Setter
public class PedidoDTO {
    private Integer cliente;
    private BigDecimal total;
    private List<ItemPedidoDTO> items;
}
