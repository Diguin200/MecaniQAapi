package desenvolvimento.mecaniqa_api.controller;

import desenvolvimento.mecaniqa_api.dto.PecaDTO;
import desenvolvimento.mecaniqa_api.exception.DadosInvalidosException;
import desenvolvimento.mecaniqa_api.exception.PersistenciaException;
import desenvolvimento.mecaniqa_api.exception.RecursoNaoEncontradoException;
import desenvolvimento.mecaniqa_api.exception.RegraDeNegocioException;
import desenvolvimento.mecaniqa_api.service.PecaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pecas")
public class PecaController {

    private final PecaService service;

    public PecaController(PecaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> listarTodas() {
        try {
            return ResponseEntity.ok(service.listarTodas());
        } catch (PersistenciaException e) {
            return RespostaErro.de(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> buscarPorCodigo(@PathVariable String codigo) {
        try {
            return ResponseEntity.ok(service.buscarPorCodigo(codigo));
        } catch (RecursoNaoEncontradoException e) {
            return RespostaErro.de(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (PersistenciaException e) {
            return RespostaErro.de(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody PecaDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(dto));
        } catch (DadosInvalidosException e) {
            return RespostaErro.de(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (PersistenciaException e) {
            return RespostaErro.de(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> atualizar(@PathVariable String codigo, @RequestBody PecaDTO dto) {
        try {
            return ResponseEntity.ok(service.atualizar(codigo, dto));
        } catch (DadosInvalidosException e) {
            return RespostaErro.de(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (RecursoNaoEncontradoException e) {
            return RespostaErro.de(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (PersistenciaException e) {
            return RespostaErro.de(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<?> excluir(@PathVariable String codigo) {
        try {
            service.excluir(codigo);
            return ResponseEntity.noContent().build();
        } catch (RecursoNaoEncontradoException e) {
            return RespostaErro.de(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (RegraDeNegocioException e) {
            return RespostaErro.de(HttpStatus.CONFLICT, e.getMessage());
        } catch (PersistenciaException e) {
            return RespostaErro.de(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
