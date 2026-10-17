
package desenvolvimento.mecaniqa_api.repository;

import desenvolvimento.mecaniqa_api.model.Servico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServicoRepository {

    private static final ServicoRepository instancia = new ServicoRepository();

    private final List<Servico> servicos = new ArrayList<>();

    private ServicoRepository() {
    }

    public static ServicoRepository getInstance() {
        return instancia;
    }

    public List<Servico> listarTodos() {
        return new ArrayList<>(servicos);
    }

    public Optional<Servico> buscarPorCodigo(String codigo) {
        return servicos.stream()
                .filter(servico -> codigo != null
                        && codigo.equals(servico.getCodigo()))
                .findFirst();
    }

    public Servico salvar(Servico servico) {
        servicos.add(servico);
        return servico;
    }

    public boolean atualizar(String codigo, Servico novoServico) {
        Optional<Servico> encontrada = buscarPorCodigo(codigo);

        if (encontrada.isPresent()) {
            Servico servico = encontrada.get();

            servico.setNome(novoServico.getNome());
            servico.setDuracaoEstimadaMinutos(
                    novoServico.getDuracaoEstimadaMinutos());
            servico.setCustoTabela(novoServico.getCustoTabela());
            servico.setDataAtualizacao(LocalDateTime.now());

            return true;
        }

        return false;
    }

    public boolean excluir(String codigo) {
        return servicos.removeIf(
                servico -> codigo != null
                        && codigo.equals(servico.getCodigo()));
    }
}
