package desenvolvimento.mecaniqa_api.service;

import desenvolvimento.mecaniqa_api.dto.ServicoDTO;
import desenvolvimento.mecaniqa_api.exception.DadosInvalidosException;
import desenvolvimento.mecaniqa_api.exception.ServicoNaoEncontradoException;
import desenvolvimento.mecaniqa_api.mapper.ServicoMapper;
import desenvolvimento.mecaniqa_api.model.Servico;
import desenvolvimento.mecaniqa_api.repository.ServicoRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ServicoService {

    private final ServicoRepository repository;

    public ServicoService(ServicoRepository repository) {
        this.repository = repository;
    }

    public List<ServicoDTO> listarTodos() {
        return repository.findAll().stream().map(ServicoMapper::paraDTO).toList();
    }

    public ServicoDTO buscarPorCodigo(String codigo) {
        Servico servico = repository.findById(codigo)
                .orElseThrow(() -> new ServicoNaoEncontradoException(codigo));
        return ServicoMapper.paraDTO(servico);
    }

    public ServicoDTO cadastrar(ServicoDTO dto) {
        validar(dto);
        Servico servico = ServicoMapper.paraEntidade(dto);
        LocalDateTime agora = LocalDateTime.now().withNano(0);
        servico.setCodigo(UUID.randomUUID().toString());
        servico.setDataCadastro(agora);
        servico.setDataAtualizacao(agora);
        return ServicoMapper.paraDTO(repository.save(servico));
    }

    public ServicoDTO atualizar(String codigo, ServicoDTO dto) {
        validar(dto);
        if (repository.findById(codigo).isEmpty()) {
            throw new ServicoNaoEncontradoException(codigo);
        }
        Servico servico = ServicoMapper.paraEntidade(dto);
        servico.setCodigo(codigo);
        servico.setDataAtualizacao(LocalDateTime.now().withNano(0));
        repository.update(codigo, servico);
        return buscarPorCodigo(codigo);
    }

    public void excluir(String codigo) {
        if (!repository.deleteById(codigo)) {
            throw new ServicoNaoEncontradoException(codigo);
        }
    }

    private void validar(ServicoDTO dto) {
        boolean invalido = dto == null
                || dto.getNome() == null
                || dto.getNome().isBlank()
                || dto.getDuracaoEstimadaMinutos() <= 0
                || dto.getCustoTabela() == null
                || dto.getCustoTabela().compareTo(BigDecimal.ZERO) < 0;
        if (invalido) {
            throw new DadosInvalidosException("Informe nome, duração positiva e custo válido.");
        }
    }
}
