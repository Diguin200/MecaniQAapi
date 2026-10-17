
package desenvolvimento.mecaniqa_api.model;

public class OrdemServico {

    private Long id;
    private String descricao;
    private String nomeCliente;
    private StatusOrdemServico status;

    public OrdemServico(
            Long id,
            String descricao,
            String nomeCliente,
            StatusOrdemServico status) {
        this.id = id;
        this.descricao = descricao;
        this.nomeCliente = nomeCliente;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public StatusOrdemServico getStatus() {
        return status;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public void setStatus(StatusOrdemServico status) {
        if (status == null) {
            throw new IllegalArgumentException("O status é obrigatório.");
        }
        this.status = status;
    }
}
