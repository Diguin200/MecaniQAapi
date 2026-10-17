
package desenvolvimento.mecaniqa_api.dto;

import desenvolvimento.mecaniqa_api.model.CategoriaPeca;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PecaDTO {

    private String codigo;
    private String codigoBarras;
    private String nome;
    private String fornecedor;
    private int quantidadeEstoque;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    private String tamanho;
    private String cor;
    private CategoriaPeca categoria;

    public PecaDTO() {
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getFornecedor() { return fornecedor; }
    public void setFornecedor(String fornecedor) { this.fornecedor = fornecedor; }

    public int getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(int quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }

    public BigDecimal getPrecoCusto() { return precoCusto; }
    public void setPrecoCusto(BigDecimal precoCusto) { this.precoCusto = precoCusto; }

    public BigDecimal getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(BigDecimal precoVenda) { this.precoVenda = precoVenda; }

    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public String getTamanho() { return tamanho; }
    public void setTamanho(String tamanho) { this.tamanho = tamanho; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    public CategoriaPeca getCategoria() { return categoria; }
    public void setCategoria(CategoriaPeca categoria) { this.categoria = categoria; }
}
