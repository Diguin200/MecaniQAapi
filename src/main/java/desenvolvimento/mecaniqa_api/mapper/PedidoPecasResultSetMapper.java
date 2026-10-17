package desenvolvimento.mecaniqa_api.mapper;

import desenvolvimento.mecaniqa_api.model.PedidoPecas;
import desenvolvimento.mecaniqa_api.model.StatusPedidoPecas;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** Lê o cabeçalho do pedido; os itens são carregados pelo repositório. */
public class PedidoPecasResultSetMapper implements ResultSetMapper<PedidoPecas> {

    @Override
    public PedidoPecas map(ResultSet rs) throws SQLException {
        return new PedidoPecas(
                rs.getLong("id"),
                StatusPedidoPecas.valueOf(rs.getString("status")),
                new ArrayList<>());
    }
}
