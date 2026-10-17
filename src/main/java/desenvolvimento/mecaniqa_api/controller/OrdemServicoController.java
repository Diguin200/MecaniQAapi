
package desenvolvimento.mecaniqa_api.controller;

import desenvolvimento.mecaniqa_api.builder.OrdemServicoBuilder;
import desenvolvimento.mecaniqa_api.dto.OrdemServicoDTO;
import desenvolvimento.mecaniqa_api.mapper.OrdemServicoMapper;
import desenvolvimento.mecaniqa_api.model.OrdemServico;
import desenvolvimento.mecaniqa_api.model.StatusOrdemServico;
import desenvolvimento.mecaniqa_api.repository.OrdemServicoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordens-servico")
public class OrdemServicoController {

    private final OrdemServicoRepository repository =
            OrdemServicoRepository.getInstance();

    @PostMapping
    public ResponseEntity<OrdemServicoDTO> criar(
            @RequestBody OrdemServicoDTO dto) {

        try {
            OrdemServico ordem = new OrdemServicoBuilder()
                    .descricao(dto.getDescricao())
                    .nomeCliente(dto.getNomeCliente())
                    .build();

            OrdemServico salva = repository.salvar(ordem);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(OrdemServicoMapper.paraDTO(salva));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<OrdemServicoDTO>> listar() {
        List<OrdemServicoDTO> ordens = repository.listarTodos()
                .stream()
                .map(OrdemServicoMapper::paraDTO)
                .toList();

        return ResponseEntity.ok(ordens);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrdemServicoDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestBody OrdemServicoDTO dto) {

        if (dto.getStatus() == null) {
            return ResponseEntity.badRequest().build();
        }

        boolean atualizado = repository.atualizarStatus(id, dto.getStatus());

        if (!atualizado) {
            return ResponseEntity.notFound().build();
        }

        OrdemServico ordem = repository.buscarPorId(id).orElseThrow();

        return ResponseEntity.ok(OrdemServicoMapper.paraDTO(ordem));
    }
}
