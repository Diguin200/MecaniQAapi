package desenvolvimento.mecaniqa_api.exception;

/** Falha de acesso ao banco de dados (resulta em HTTP 500). */
public class PersistenciaException extends RuntimeException {

    public PersistenciaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
