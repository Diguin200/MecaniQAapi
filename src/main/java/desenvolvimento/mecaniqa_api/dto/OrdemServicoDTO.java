
package desenvolvimento.mecaniqa_api.dto;

import desenvolvimento.mecaniqa_api.model.StatusOrdemServico;

public class OrdemServicoDTO {

    private Long id;
    private String descricao;
    private String nomeCliente;
    private StatusOrdemServico status;

    public OrdemServicoDTO() {
    }

    public OrdemServicoDTO(Long id, String descricao,
            String nomeCliente, StatusOrdemServico status) {
        this.id = id;
        this.descricao = descricao;
        this.nomeCliente = nomeCliente;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public StatusOrdemServico getStatus() {
        return status;
    }

    public void setStatus(StatusOrdemServico status) {
        this.status = status;
    }
}
