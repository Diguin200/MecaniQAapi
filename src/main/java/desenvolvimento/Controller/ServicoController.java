
package desenvolvimento.mecaniqa_api.controller;

import desenvolvimento.mecaniqa_api.dto.ServicoDTO;
import desenvolvimento.mecaniqa_api.mapper.ServicoMapper;
import desenvolvimento.mecaniqa_api.model.Servico;
import desenvolvimento.mecaniqa_api.repository.ServicoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoRepository repository =
            ServicoRepository.getInstance();

    @GetMapping
    public ResponseEntity<List<ServicoDTO>> listarTodos() {
        List<ServicoDTO> servicos = repository.listarTodos()
                .stream()
                .map(ServicoMapper::paraDTO)
                .toList();

        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ServicoDTO> buscarPorCodigo(
            @PathVariable String codigo) {

        return repository.buscarPorCodigo(codigo)
                .map(ServicoMapper::paraDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody ServicoDTO dto) {

        Servico servico = ServicoMapper.paraEntidade(dto);

        if (!dadosValidos(servico)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro",
                            "Informe nome, duração positiva e custo válido."));
        }

        servico.setCodigo(UUID.randomUUID().toString());
        servico.setDataCadastro(LocalDateTime.now());
        servico.setDataAtualizacao(LocalDateTime.now());

        Servico salvo = repository.salvar(servico);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ServicoMapper.paraDTO(salvo));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> atualizar(
            @PathVariable String codigo,
            @RequestBody ServicoDTO dto) {

        Servico servico = ServicoMapper.paraEntidade(dto);

        if (!dadosValidos(servico)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro",
                            "Informe nome, duração positiva e custo válido."));
        }

        servico.setDataAtualizacao(LocalDateTime.now());

        boolean atualizado = repository.atualizar(codigo, servico);

        if (!atualizado) {
            return ResponseEntity.notFound().build();
        }

        return repository.buscarPorCodigo(codigo)
                .map(ServicoMapper::paraDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> excluir(
            @PathVariable String codigo) {

        boolean excluido = repository.excluir(codigo);

        if (!excluido) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    private boolean dadosValidos(Servico servico) {
        return servico != null
                && servico.getNome() != null
                && !servico.getNome().isBlank()
                && servico.getDuracaoEstimadaMinutos() > 0
                && servico.getCustoTabela() != null
                && servico.getCustoTabela()
                        .compareTo(BigDecimal.ZERO) >= 0;
    }
}
    