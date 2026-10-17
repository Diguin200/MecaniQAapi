package desenvolvimento.mecaniqa_api.exception;

/** Base das exceções de "não encontrado" (resulta em HTTP 404). */
public abstract class RecursoNaoEncontradoException extends RuntimeException {

    protected RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
