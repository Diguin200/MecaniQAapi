
package desenvolvimento.mecaniqa_api.repository;

import desenvolvimento.mecaniqa_api.model.Peca;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PecaRepository {

    private static final PecaRepository instancia = new PecaRepository();

    private final List<Peca> pecas = new ArrayList<>();

    private PecaRepository() {
    }

    public static PecaRepository getInstance() {
        return instancia;
    }

    public List<Peca> listarTodos() {
        return new ArrayList<>(pecas);
    }

    public Optional<Peca> buscarPorCodigo(String codigo) {
        return pecas.stream()
        .filter(peca -> codigo.equals(peca.getCodigo()))
                .findFirst();
    }

    public Peca salvar(Peca peca) {
        pecas.add(peca);
        return peca;
    }

    public boolean atualizar(String codigo, Peca novaPeca) {
        Optional<Peca> encontrada = buscarPorCodigo(codigo);

        if (encontrada.isPresent()) {
            Peca peca = encontrada.get();

            peca.setCodigoBarras(novaPeca.getCodigoBarras());
            peca.setNome(novaPeca.getNome());
            peca.setFornecedor(novaPeca.getFornecedor());
            peca.setQuantidadeEstoque(novaPeca.getQuantidadeEstoque());
            peca.setPrecoCusto(novaPeca.getPrecoCusto());
            peca.setPrecoVenda(novaPeca.getPrecoVenda());
            peca.setTamanho(novaPeca.getTamanho());
            peca.setCor(novaPeca.getCor());
            peca.setCategoria(novaPeca.getCategoria());
            peca.setDataAtualizacao(java.time.LocalDateTime.now());

            return true;
        }

        return false;
    }

    public boolean excluir(String codigo) {
        return pecas.removeIf(peca -> peca.getCodigo().equals(codigo));
    }
}

