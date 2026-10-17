
package desenvolvimento.mecaniqa_api.repository;

import desenvolvimento.mecaniqa_api.model.PedidoPecas;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoPecasRepository {

    private static final PedidoPecasRepository INSTANCE =
            new PedidoPecasRepository();

    private final List<PedidoPecas> pedidos = new ArrayList<>();
    private Long proximoId = 1L;

    private PedidoPecasRepository() {
    }

    public static PedidoPecasRepository getInstance() {
        return INSTANCE;
    }

    public PedidoPecas salvar() {
        PedidoPecas pedido = new PedidoPecas(proximoId++);
        pedidos.add(pedido);
        return pedido;
    }

    public List<PedidoPecas> listarTodos() {
        return new ArrayList<>(pedidos);
    }

    public Optional<PedidoPecas> buscarPorId(Long id) {
        return pedidos.stream()
                .filter(pedido -> pedido.getId().equals(id))
                .findFirst();
    }
}
