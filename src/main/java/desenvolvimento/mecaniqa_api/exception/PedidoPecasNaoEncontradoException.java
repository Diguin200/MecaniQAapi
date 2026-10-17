package desenvolvimento.mecaniqa_api.exception;

public class PedidoPecasNaoEncontradoException extends RecursoNaoEncontradoException {

    public PedidoPecasNaoEncontradoException(Long identificador) {
        super("Pedido de peças não encontrado: " + identificador);
    }
}
