package desenvolvimento.mecaniqa_api.repository;

import desenvolvimento.mecaniqa_api.config.ConnectionFactory;
import desenvolvimento.mecaniqa_api.exception.PersistenciaException;
import desenvolvimento.mecaniqa_api.mapper.OrdemServicoResultSetMapper;
import desenvolvimento.mecaniqa_api.mapper.ResultSetMapper;
import desenvolvimento.mecaniqa_api.model.OrdemServico;
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
public class OrdemServicoRepository implements Repository<OrdemServico, Long> {

    private static final String INSERT =
            "INSERT INTO ordem_servico (descricao, nome_cliente, status) VALUES (?, ?, ?)";
    private static final String SELECT_BY_ID =
            "SELECT id, descricao, nome_cliente, status FROM ordem_servico WHERE id = ?";
    private static final String SELECT_ALL =
            "SELECT id, descricao, nome_cliente, status FROM ordem_servico ORDER BY id";
    private static final String UPDATE =
            "UPDATE ordem_servico SET descricao = ?, nome_cliente = ?, status = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM ordem_servico WHERE id = ?";

    private final ConnectionFactory connectionFactory;
    private final ResultSetMapper<OrdemServico> mapper = new OrdemServicoResultSetMapper();

    public OrdemServicoRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public OrdemServico save(OrdemServico ordem) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, ordem.getDescricao());
            ps.setString(2, ordem.getNomeCliente());
            ps.setString(3, ordem.getStatus().name());
            ps.executeUpdate();
            try (ResultSet chaves = ps.getGeneratedKeys()) {
                chaves.next();
                return new OrdemServico(
                        chaves.getLong(1),
                        ordem.getDescricao(),
                        ordem.getNomeCliente(),
                        ordem.getStatus());
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao salvar a ordem de serviço.", e);
        }
    }

    @Override
    public Optional<OrdemServico> findById(Long id) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapper.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar a ordem de serviço.", e);
        }
    }

    @Override
    public List<OrdemServico> findAll() {
        List<OrdemServico> ordens = new ArrayList<>();
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ordens.add(mapper.map(rs));
            }
            return ordens;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar as ordens de serviço.", e);
        }
    }

    @Override
    public boolean update(Long id, OrdemServico ordem) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1, ordem.getDescricao());
            ps.setString(2, ordem.getNomeCliente());
            ps.setString(3, ordem.getStatus().name());
            ps.setLong(4, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao atualizar a ordem de serviço.", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao excluir a ordem de serviço.", e);
        }
    }
}
