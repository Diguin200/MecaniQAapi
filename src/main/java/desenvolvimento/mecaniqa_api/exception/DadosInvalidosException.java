package desenvolvimento.mecaniqa_api.exception;

/** Dados de entrada inválidos (resulta em HTTP 400). */
public class DadosInvalidosException extends RuntimeException {

    public DadosInvalidosException(String mensagem) {
        super(mensagem);
    }
}
