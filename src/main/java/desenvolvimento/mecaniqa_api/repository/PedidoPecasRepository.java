package desenvolvimento.mecaniqa_api.repository;

import desenvolvimento.mecaniqa_api.config.ConnectionFactory;
import desenvolvimento.mecaniqa_api.exception.PersistenciaException;
import desenvolvimento.mecaniqa_api.mapper.ItemPedidoResultSetMapper;
import desenvolvimento.mecaniqa_api.mapper.PedidoPecasResultSetMapper;
import desenvolvimento.mecaniqa_api.mapper.ResultSetMapper;
import desenvolvimento.mecaniqa_api.model.ItemPedido;
import desenvolvimento.mecaniqa_api.model.PedidoPecas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class PedidoPecasRepository implements Repository<PedidoPecas, Long> {

    private static final String INSERT_PEDIDO = "INSERT INTO pedido_pecas (status) VALUES (?)";
    private static final String INSERT_ITEM =
            "INSERT INTO item_pedido (pedido_id, peca_codigo, quantidade) VALUES (?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT id, status FROM pedido_pecas WHERE id = ?";
    private static final String SELECT_ALL = "SELECT id, status FROM pedido_pecas ORDER BY id";
    private static final String SELECT_ITENS =
            "SELECT p.*, i.quantidade FROM item_pedido i "
            + "JOIN peca p ON p.codigo = i.peca_codigo WHERE i.pedido_id = ? ORDER BY i.id";
    private static final String UPDATE_PEDIDO = "UPDATE pedido_pecas SET status = ? WHERE id = ?";
    private static final String DELETE_ITENS = "DELETE FROM item_pedido WHERE pedido_id = ?";
    private static final String DELETE_PEDIDO = "DELETE FROM pedido_pecas WHERE id = ?";

    private final ConnectionFactory connectionFactory;
    private final ResultSetMapper<PedidoPecas> pedidoMapper = new PedidoPecasResultSetMapper();
    private final ResultSetMapper<ItemPedido> itemMapper = new ItemPedidoResultSetMapper();

    public PedidoPecasRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public PedidoPecas save(PedidoPecas pedido) {
        try (Connection conn = connectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                long id;
                try (PreparedStatement ps =
                             conn.prepareStatement(INSERT_PEDIDO, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, pedido.getStatus().name());
                    ps.executeUpdate();
                    try (ResultSet chaves = ps.getGeneratedKeys()) {
                        chaves.next();
                        id = chaves.getLong(1);
                    }
                }
                inserirItens(conn, id, pedido.getItens());
                conn.commit();
                return new PedidoPecas(id, pedido.getStatus(), pedido.getItens());
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao salvar o pedido de peças.", e);
        }
    }

    @Override
    public Optional<PedidoPecas> findById(Long id) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
            ps.setLong(1, id);
            PedidoPecas cabecalho;
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                cabecalho = pedidoMapper.map(rs);
            }
            return Optional.of(comItens(conn, cabecalho));
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar o pedido de peças.", e);
        }
    }

    @Override
    public List<PedidoPecas> findAll() {
        try (Connection conn = connectionFactory.getConnection()) {
            List<PedidoPecas> cabecalhos = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cabecalhos.add(pedidoMapper.map(rs));
                }
            }
            List<PedidoPecas> pedidos = new ArrayList<>();
            for (PedidoPecas cabecalho : cabecalhos) {
                pedidos.add(comItens(conn, cabecalho));
            }
            return pedidos;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar os pedidos de peças.", e);
        }
    }

    @Override
    public boolean update(Long id, PedidoPecas pedido) {
        try (Connection conn = connectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int atualizados;
                try (PreparedStatement ps = conn.prepareStatement(UPDATE_PEDIDO)) {
                    ps.setString(1, pedido.getStatus().name());
                    ps.setLong(2, id);
                    atualizados = ps.executeUpdate();
                }
                if (atualizados == 0) {
                    conn.rollback();
                    return false;
                }
                try (PreparedStatement ps = conn.prepareStatement(DELETE_ITENS)) {
                    ps.setLong(1, id);
                    ps.executeUpdate();
                }
                inserirItens(conn, id, pedido.getItens());
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao atualizar o pedido de peças.", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_PEDIDO)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao excluir o pedido de peças.", e);
        }
    }

    private void inserirItens(Connection conn, long pedidoId, List<ItemPedido> itens)
            throws SQLException {
        if (itens.isEmpty()) {
            return;
        }
        try (PreparedStatement ps = conn.prepareStatement(INSERT_ITEM)) {
            for (ItemPedido item : itens) {
                ps.setLong(1, pedidoId);
                ps.setString(2, item.getPeca().getCodigo());
                ps.setInt(3, item.getQuantidade());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private PedidoPecas comItens(Connection conn, PedidoPecas cabecalho) throws SQLException {
        List<ItemPedido> itens = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(SELECT_ITENS)) {
            ps.setLong(1, cabecalho.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    itens.add(itemMapper.map(rs));
                }
            }
        }
        return new PedidoPecas(cabecalho.getId(), cabecalho.getStatus(), itens);
    }
}
