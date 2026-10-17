package desenvolvimento.mecaniqa_api.exception;

public class PecaNaoEncontradaException extends RecursoNaoEncontradoException {

    public PecaNaoEncontradaException(String identificador) {
        super("Peça não encontrada: " + identificador);
    }
}
