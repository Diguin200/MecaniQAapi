
package desenvolvimento.mecaniqa_api.model;

import java.util.ArrayList;
import java.util.List;

public class PedidoPecas {

    private Long id;
    private StatusPedidoPecas status;
    private List<ItemPedido> itens;

    public PedidoPecas(Long id) {
        this.id = id;
        this.status = StatusPedidoPecas.ORCANDO;
        this.itens = new ArrayList<>();
    }

    /** Reconstrói um pedido já persistido (usado pelo mapper do banco). */
    public PedidoPecas(Long id, StatusPedidoPecas status, List<ItemPedido> itens) {
        this.id = id;
        this.status = status;
        this.itens = new ArrayList<>(itens);
    }

    public void adicionarItem(ItemPedido item) {
        if (status != StatusPedidoPecas.ORCANDO) {
            throw new IllegalStateException(
                "Só é possível adicionar peças enquanto o pedido está sendo orçado."
            );
        }

        if (item == null) {
            throw new IllegalArgumentException("O item é obrigatório.");
        }

        itens.add(item);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusPedidoPecas getStatus() {
        return status;
    }

    public void setStatus(StatusPedidoPecas status) {
        if (status == null) {
            throw new IllegalArgumentException("O status é obrigatório.");
        }

        this.status = status;
    }

    public List<ItemPedido> getItens() {
        return new ArrayList<>(itens);
    }
}
    