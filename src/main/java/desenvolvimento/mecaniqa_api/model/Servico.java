
package desenvolvimento.mecaniqa_api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Servico {

    private String codigo;
    private String nome;
    private int duracaoEstimadaMinutos; 
    private BigDecimal custoTabela;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;

    public Servico() {
    }

    public Servico(String codigo, String nome,
                   int duracaoEstimadaMinutos, BigDecimal custoTabela) {
        this.codigo = codigo;
        this.nome = nome;
        this.duracaoEstimadaMinutos = duracaoEstimadaMinutos;
        this.custoTabela = custoTabela;
        this.dataCadastro = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
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
