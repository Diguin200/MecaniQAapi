package desenvolvimento.mecaniqa_api.controller;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/** Monta a resposta de erro devolvida pelos controllers nos blocos catch. */
final class RespostaErro {

    private RespostaErro() {
    }

    static ResponseEntity<Map<String, String>> de(HttpStatus status, String mensagem) {
        return ResponseEntity.status(status).body(Map.of("erro", mensagem));
    }
}
