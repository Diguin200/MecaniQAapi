package desenvolvimento.mecaniqa_api.repository;

import desenvolvimento.mecaniqa_api.config.ConnectionFactory;
import desenvolvimento.mecaniqa_api.exception.PersistenciaException;
import desenvolvimento.mecaniqa_api.mapper.ResultSetMapper;
import desenvolvimento.mecaniqa_api.mapper.ServicoResultSetMapper;
import desenvolvimento.mecaniqa_api.model.Servico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ServicoRepository implements Repository<Servico, String> {

    private static final String INSERT =
            "INSERT INTO servico (codigo, nome, duracao_estimada_minutos, custo_tabela, "
            + "data_cadastro, data_atualizacao) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT * FROM servico WHERE codigo = ?";
    private static final String SELECT_ALL = "SELECT * FROM servico ORDER BY nome";
    private static final String UPDATE =
            "UPDATE servico SET nome = ?, duracao_estimada_minutos = ?, custo_tabela = ?, "
            + "data_atualizacao = ? WHERE codigo = ?";
    private static final String DELETE = "DELETE FROM servico WHERE codigo = ?";

    private final ConnectionFactory connectionFactory;
    private final ResultSetMapper<Servico> mapper = new ServicoResultSetMapper();

    public ServicoRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Servico save(Servico servico) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            ps.setString(1, servico.getCodigo());
            ps.setString(2, servico.getNome());
            ps.setInt(3, servico.getDuracaoEstimadaMinutos());
            ps.setBigDecimal(4, servico.getCustoTabela());
            ps.setTimestamp(5, Timestamp.valueOf(servico.getDataCadastro()));
            ps.setTimestamp(6, Timestamp.valueOf(servico.getDataAtualizacao()));
            ps.executeUpdate();
            return servico;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao salvar o serviço.", e);
        }
    }

    @Override
    public Optional<Servico> findById(String codigo) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapper.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar o serviço.", e);
        }
    }

    @Override
    public List<Servico> findAll() {
        List<Servico> servicos = new ArrayList<>();
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                servicos.add(mapper.map(rs));
            }
            return servicos;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar os serviços.", e);
        }
    }

    @Override
    public boolean update(String codigo, Servico servico) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1, servico.getNome());
            ps.setInt(2, servico.getDuracaoEstimadaMinutos());
            ps.setBigDecimal(3, servico.getCustoTabela());
            ps.setTimestamp(4, Timestamp.valueOf(servico.getDataAtualizacao()));
            ps.setString(5, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao atualizar o serviço.", e);
        }
    }

    @Override
    public boolean deleteById(String codigo) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE)) {
            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao excluir o serviço.", e);
        }
    }
}
