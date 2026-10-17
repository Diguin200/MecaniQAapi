package desenvolvimento.mecaniqa_api.mapper;

import desenvolvimento.mecaniqa_api.model.ItemPedido;
import desenvolvimento.mecaniqa_api.model.Peca;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Lê o resultado do JOIN entre item_pedido e peca. */
public class ItemPedidoResultSetMapper implements ResultSetMapper<ItemPedido> {

    private final ResultSetMapper<Peca> pecaMapper = new PecaResultSetMapper();

    @Override
    public ItemPedido map(ResultSet rs) throws SQLException {
        Peca peca = pecaMapper.map(rs);
        return new ItemPedido(peca, rs.getInt("quantidade"));
    }
}
