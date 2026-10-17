
package desenvolvimento.mecaniqa_api.model;

public class ItemPedido {

    private Peca peca;
    private int quantidade;

    public ItemPedido(Peca peca, int quantidade) {
        if (peca == null) {
            throw new IllegalArgumentException("A peça é obrigatória.");
        }

        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        this.peca = peca;
        this.quantidade = quantidade;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        if (peca == null) {
            throw new IllegalArgumentException("A peça é obrigatória.");
        }
        this.peca = peca;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        this.quantidade = quantidade;
    }
}
