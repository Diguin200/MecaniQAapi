package desenvolvimento.mecaniqa_api.controller;

import desenvolvimento.mecaniqa_api.dto.OrdemServicoDTO;
import desenvolvimento.mecaniqa_api.exception.DadosInvalidosException;
import desenvolvimento.mecaniqa_api.exception.PersistenciaException;
import desenvolvimento.mecaniqa_api.exception.RecursoNaoEncontradoException;
import desenvolvimento.mecaniqa_api.service.OrdemServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ordens-servico")
public class OrdemServicoController {

    private final OrdemServicoService service;

    public OrdemServicoController(OrdemServicoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody OrdemServicoDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
        } catch (DadosInvalidosException e) {
            return RespostaErro.de(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (PersistenciaException e) {
            return RespostaErro.de(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok(service.listar());
        } catch (PersistenciaException e) {
            return RespostaErro.de(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (RecursoNaoEncontradoException e) {
            return RespostaErro.de(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (PersistenciaException e) {
            return RespostaErro.de(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id,
                                             @RequestBody OrdemServicoDTO dto) {
        try {
            return ResponseEntity.ok(service.atualizarStatus(id, dto));
        } catch (DadosInvalidosException e) {
            return RespostaErro.de(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (RecursoNaoEncontradoException e) {
            return RespostaErro.de(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (PersistenciaException e) {
            return RespostaErro.de(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        try {
            service.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RecursoNaoEncontradoException e) {
            return RespostaErro.de(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (PersistenciaException e) {
            return RespostaErro.de(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
