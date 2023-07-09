package org.kamicaze.rest.dto;

import lombok.Getter;
import lombok.Setter;
import org.kamicaze.validation.NotEmptyList;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "Informe o codigo do cliente")
    private Integer cliente;
    @NotNull(message = "total invalido")
    private BigDecimal total;
    @NotEmptyList(message = "Não pode realizar um pedido sem elementos")
    private List<ItemPedidoDTO> items;
}
