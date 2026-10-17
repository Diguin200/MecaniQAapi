
package desenvolvimento.mecaniqa_api.controller;

import desenvolvimento.mecaniqa_api.dto.PecaDTO;
import desenvolvimento.mecaniqa_api.mapper.PecaMapper;
import desenvolvimento.mecaniqa_api.model.Peca;
import desenvolvimento.mecaniqa_api.repository.PecaRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/pecas")
public class PecaController {

    private final PecaRepository repository =
            PecaRepository.getInstance();

    @GetMapping
    public ResponseEntity<List<PecaDTO>> listarTodas() {
        List<PecaDTO> pecas = repository.listarTodos()
                .stream()
                .map(PecaMapper::paraDTO)
                .toList();

        return ResponseEntity.ok(pecas);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<PecaDTO> buscarPorCodigo(
            @PathVariable String codigo) {

        return repository.buscarPorCodigo(codigo)
                .map(PecaMapper::paraDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody PecaDTO dto) {

        Peca peca = PecaMapper.paraEntidade(dto);

        if (!dadosValidos(peca)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro",
                            "Informe nome, categoria, estoque e precos validos."));
        }

        peca.setCodigo(UUID.randomUUID().toString());
        peca.setDataCadastro(LocalDateTime.now());
        peca.setDataAtualizacao(LocalDateTime.now());

        Peca salva = repository.salvar(peca);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PecaMapper.paraDTO(salva));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> atualizar(
            @PathVariable String codigo,
            @RequestBody PecaDTO dto) {

        Peca peca = PecaMapper.paraEntidade(dto);

        if (!dadosValidos(peca)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro",
                            "Informe nome, categoria, estoque e precos validos."));
        }

        boolean atualizado = repository.atualizar(codigo, peca);

        if (!atualizado) {
            return ResponseEntity.notFound().build();
        }

        return repository.buscarPorCodigo(codigo)
                .map(PecaMapper::paraDTO)
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

    private boolean dadosValidos(Peca peca) {
        return peca != null
                && peca.getNome() != null
                && !peca.getNome().isBlank()
                && peca.getCategoria() != null
                && peca.getQuantidadeEstoque() >= 0
                && peca.getPrecoCusto() != null
                && peca.getPrecoVenda() != null
                && peca.getPrecoCusto().compareTo(BigDecimal.ZERO) >= 0
                && peca.getPrecoVenda().compareTo(BigDecimal.ZERO) >= 0;
    }
}
