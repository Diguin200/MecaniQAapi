
package desenvolvimento.mecaniqa_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ServicoDTO {

    private String codigo;
    private String nome;
    private int duracaoEstimadaMinutos;
    private BigDecimal custoTabela;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;

    public ServicoDTO() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDuracaoEstimadaMinutos() {
        return duracaoEstimadaMinutos;
    }

    public void setDuracaoEstimadaMinutos(int duracaoEstimadaMinutos) {
        this.duracaoEstimadaMinutos = duracaoEstimadaMinutos;
    }

    public BigDecimal getCustoTabela() {
        return custoTabela;
    }

    public void setCustoTabela(BigDecimal custoTabela) {
        this.custoTabela = custoTabela;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
