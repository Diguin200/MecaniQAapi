
package desenvolvimento.mecaniqa_api.builder;

import desenvolvimento.mecaniqa_api.model.OrdemServico;
import desenvolvimento.mecaniqa_api.model.StatusOrdemServico;

public class OrdemServicoBuilder {

    private Long id;
    private String descricao;
    private String nomeCliente;
    private StatusOrdemServico status = StatusOrdemServico.ABERTO;

    public OrdemServicoBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public OrdemServicoBuilder descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public OrdemServicoBuilder nomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
        return this;
    }

    public OrdemServicoBuilder status(StatusOrdemServico status) {
        this.status = status;
        return this;
    }

    public OrdemServico build() {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição é obrigatória.");
        }

        if (nomeCliente == null || nomeCliente.isBlank()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }

        if (status == null) {
            throw new IllegalArgumentException("O status é obrigatório.");
        }

        return new OrdemServico(id, descricao, nomeCliente, status);
    }
}
