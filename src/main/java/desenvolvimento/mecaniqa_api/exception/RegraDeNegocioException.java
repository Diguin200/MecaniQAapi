package desenvolvimento.mecaniqa_api.exception;

/** Violação de regra de negócio (resulta em HTTP 409). */
public class RegraDeNegocioException extends RuntimeException {

    public RegraDeNegocioException(String mensagem) {
        super(mensagem);
    }
}
