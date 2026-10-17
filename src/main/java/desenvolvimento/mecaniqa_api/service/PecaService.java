package desenvolvimento.mecaniqa_api.service;

import desenvolvimento.mecaniqa_api.dto.PecaDTO;
import desenvolvimento.mecaniqa_api.exception.DadosInvalidosException;
import desenvolvimento.mecaniqa_api.exception.PecaNaoEncontradaException;
import desenvolvimento.mecaniqa_api.mapper.PecaMapper;
import desenvolvimento.mecaniqa_api.model.Peca;
import desenvolvimento.mecaniqa_api.repository.PecaRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PecaService {

    private final PecaRepository repository;

    public PecaService(PecaRepository repository) {
        this.repository = repository;
    }

    public List<PecaDTO> listarTodas() {
        return repository.findAll().stream().map(PecaMapper::paraDTO).toList();
    }

    public PecaDTO buscarPorCodigo(String codigo) {
        Peca peca = repository.findById(codigo)
                .orElseThrow(() -> new PecaNaoEncontradaException(codigo));
        return PecaMapper.paraDTO(peca);
    }

    public PecaDTO cadastrar(PecaDTO dto) {
        validar(dto);
        Peca peca = PecaMapper.paraEntidade(dto);
        LocalDateTime agora = LocalDateTime.now().withNano(0);
        peca.setCodigo(UUID.randomUUID().toString());
        peca.setDataCadastro(agora);
        peca.setDataAtualizacao(agora);
        return PecaMapper.paraDTO(repository.save(peca));
    }

    public PecaDTO atualizar(String codigo, PecaDTO dto) {
        validar(dto);
        if (repository.findById(codigo).isEmpty()) {
            throw new PecaNaoEncontradaException(codigo);
        }
        Peca peca = PecaMapper.paraEntidade(dto);
        peca.setCodigo(codigo);
        peca.setDataAtualizacao(LocalDateTime.now().withNano(0));
        repository.update(codigo, peca);
        return buscarPorCodigo(codigo);
    }

    public void excluir(String codigo) {
        if (!repository.deleteById(codigo)) {
            throw new PecaNaoEncontradaException(codigo);
        }
    }

    private void validar(PecaDTO dto) {
        boolean invalido = dto == null
                || dto.getNome() == null
                || dto.getNome().isBlank()
                || dto.getCategoria() == null
                || dto.getQuantidadeEstoque() < 0
                || dto.getPrecoCusto() == null
                || dto.getPrecoVenda() == null
                || dto.getPrecoCusto().compareTo(BigDecimal.ZERO) < 0
                || dto.getPrecoVenda().compareTo(BigDecimal.ZERO) < 0;
        if (invalido) {
            throw new DadosInvalidosException(
                    "Informe nome, categoria, estoque e precos validos.");
        }
    }
}
