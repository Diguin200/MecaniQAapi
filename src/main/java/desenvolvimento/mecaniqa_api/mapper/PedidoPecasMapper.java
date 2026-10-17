
package desenvolvimento.mecaniqa_api.mapper;

import desenvolvimento.mecaniqa_api.dto.ItemPedidoDTO;
import desenvolvimento.mecaniqa_api.dto.PedidoPecasDTO;
import desenvolvimento.mecaniqa_api.model.PedidoPecas;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoPecasMapper {

    public static PedidoPecasDTO paraDTO(PedidoPecas pedido) {
        List<ItemPedidoDTO> itens = pedido.getItens().stream()
                .map(item -> new ItemPedidoDTO(
                        item.getPeca().getCodigo(),
                        item.getQuantidade()))
                .collect(Collectors.toList());

        return new PedidoPecasDTO(
                pedido.getId(),
                pedido.getStatus(),
                itens);
    }
}
