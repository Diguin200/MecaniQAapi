package desenvolvimento.mecaniqa_api.exception;

public class ServicoNaoEncontradoException extends RecursoNaoEncontradoException {

    public ServicoNaoEncontradoException(String identificador) {
        super("Serviço não encontrado: " + identificador);
    }
}
