
package desenvolvimento.mecaniqa_api.repository;

import desenvolvimento.mecaniqa_api.model.OrdemServico;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdemServicoRepository {

    private static final OrdemServicoRepository INSTANCE =
            new OrdemServicoRepository();

    private final List<OrdemServico> ordens = new ArrayList<>();
    private Long proximoId = 1L;

    private OrdemServicoRepository() {
    }

    public static OrdemServicoRepository getInstance() {
        return INSTANCE;
    }

    public OrdemServico salvar(OrdemServico ordem) {
        OrdemServico novaOrdem = new OrdemServico(
                proximoId++,
                ordem.getDescricao(),
                ordem.getNomeCliente(),
                ordem.getStatus()
        );

        ordens.add(novaOrdem);
        return novaOrdem;
    }

    public List<OrdemServico> listarTodos() {
        return new ArrayList<>(ordens);
    }

    public Optional<OrdemServico> buscarPorId(Long id) {
        return ordens.stream()
                .filter(ordem -> ordem.getId().equals(id))
                .findFirst();
    }

    public boolean atualizarStatus(Long id,
            desenvolvimento.mecaniqa_api.model.StatusOrdemServico status) {
        Optional<OrdemServico> ordem = buscarPorId(id);

        if (ordem.isEmpty()) {
            return false;
        }

        ordem.get().setStatus(status);
        return true;
    }
}
