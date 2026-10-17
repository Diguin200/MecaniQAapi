package desenvolvimento.mecaniqa_api.service;

import desenvolvimento.mecaniqa_api.builder.OrdemServicoBuilder;
import desenvolvimento.mecaniqa_api.dto.OrdemServicoDTO;
import desenvolvimento.mecaniqa_api.exception.DadosInvalidosException;
import desenvolvimento.mecaniqa_api.exception.OrdemServicoNaoEncontradaException;
import desenvolvimento.mecaniqa_api.mapper.OrdemServicoMapper;
import desenvolvimento.mecaniqa_api.model.OrdemServico;
import desenvolvimento.mecaniqa_api.repository.OrdemServicoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrdemServicoService {

    private final OrdemServicoRepository repository;

    public OrdemServicoService(OrdemServicoRepository repository) {
        this.repository = repository;
    }

    public OrdemServicoDTO criar(OrdemServicoDTO dto) {
        if (dto == null) {
            throw new DadosInvalidosException("Os dados da ordem de serviço são obrigatórios.");
        }
        try {
            OrdemServico ordem = new OrdemServicoBuilder()
                    .descricao(dto.getDescricao())
                    .nomeCliente(dto.getNomeCliente())
                    .build();
            return OrdemServicoMapper.paraDTO(repository.save(ordem));
        } catch (IllegalArgumentException e) {
            throw new DadosInvalidosException(e.getMessage());
        }
    }

    public List<OrdemServicoDTO> listar() {
        return repository.findAll().stream().map(OrdemServicoMapper::paraDTO).toList();
    }

    public OrdemServicoDTO buscarPorId(Long id) {
        return OrdemServicoMapper.paraDTO(buscarEntidade(id));
    }

    public OrdemServicoDTO atualizarStatus(Long id, OrdemServicoDTO dto) {
        if (dto == null || dto.getStatus() == null) {
            throw new DadosInvalidosException("O status é obrigatório.");
        }
        OrdemServico ordem = buscarEntidade(id);
        ordem.setStatus(dto.getStatus());
        repository.update(id, ordem);
        return OrdemServicoMapper.paraDTO(ordem);
    }

    public void excluir(Long id) {
        if (!repository.deleteById(id)) {
            throw new OrdemServicoNaoEncontradaException(id);
        }
    }

    private OrdemServico buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new OrdemServicoNaoEncontradaException(id));
    }
}
