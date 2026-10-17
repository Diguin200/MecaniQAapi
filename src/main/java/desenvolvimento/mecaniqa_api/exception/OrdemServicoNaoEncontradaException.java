package desenvolvimento.mecaniqa_api.exception;

public class OrdemServicoNaoEncontradaException extends RecursoNaoEncontradoException {

    public OrdemServicoNaoEncontradaException(Long identificador) {
        super("Ordem de serviço não encontrada: " + identificador);
    }
}
