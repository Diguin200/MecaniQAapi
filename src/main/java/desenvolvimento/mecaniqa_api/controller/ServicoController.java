package desenvolvimento.mecaniqa_api.controller;

import desenvolvimento.mecaniqa_api.dto.ServicoDTO;
import desenvolvimento.mecaniqa_api.exception.DadosInvalidosException;
import desenvolvimento.mecaniqa_api.exception.PersistenciaException;
import desenvolvimento.mecaniqa_api.exception.RecursoNaoEncontradoException;
import desenvolvimento.mecaniqa_api.service.ServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoService service;

    public ServicoController(ServicoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        try {
            return ResponseEntity.ok(service.listarTodos());
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
    public ResponseEntity<?> cadastrar(@RequestBody ServicoDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(dto));
        } catch (DadosInvalidosException e) {
            return RespostaErro.de(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (PersistenciaException e) {
            return RespostaErro.de(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> atualizar(@PathVariable String codigo, @RequestBody ServicoDTO dto) {
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
        } catch (PersistenciaException e) {
            return RespostaErro.de(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
