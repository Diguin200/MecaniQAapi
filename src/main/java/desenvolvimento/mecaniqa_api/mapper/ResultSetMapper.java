package desenvolvimento.mecaniqa_api.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/** Converte a linha atual de um ResultSet em um objeto de modelo. */
@FunctionalInterface
public interface ResultSetMapper<T> {

    T map(ResultSet rs) throws SQLException;
}
