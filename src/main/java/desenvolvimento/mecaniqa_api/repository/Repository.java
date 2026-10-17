package desenvolvimento.mecaniqa_api.repository;

import java.util.List;
import java.util.Optional;

/**
 * Contrato genérico de CRUD implementado por todos os repositórios JDBC.
 *
 * @param <T>  tipo da entidade
 * @param <ID> tipo do identificador
 */
public interface Repository<T, ID> {

    T save(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    /** @return true se o registro existia e foi atualizado */
    boolean update(ID id, T entity);

    /** @return true se o registro existia e foi removido */
    boolean deleteById(ID id);
}
