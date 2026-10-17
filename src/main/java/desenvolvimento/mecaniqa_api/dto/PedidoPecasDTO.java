
package desenvolvimento.mecaniqa_api.dto;

import desenvolvimento.mecaniqa_api.model.StatusPedidoPecas;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PedidoPecasDTO {

    private Long id;
    private StatusPedidoPecas status;
    private List<ItemPedidoDTO> itens = new ArrayList<>();
    private BigDecimal valorTotal = BigDecimal.ZERO;

    public PedidoPecasDTO() {
    }

    public PedidoPecasDTO(Long id, StatusPedidoPecas status,
            List<ItemPedidoDTO> itens) {
        this.id = id;
        this.status = status;
        this.itens = itens == null ? new ArrayList<>() : new ArrayList<>(itens);
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
        this.status = status;
    }

    public List<ItemPedidoDTO> getItens() {
        return new ArrayList<>(itens);
    }

    public void setItens(List<ItemPedidoDTO> itens) {
        this.itens = itens == null ? new ArrayList<>() : new ArrayList<>(itens);
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
