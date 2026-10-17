package desenvolvimento.mecaniqa_api.repository;

import desenvolvimento.mecaniqa_api.config.ConnectionFactory;
import desenvolvimento.mecaniqa_api.exception.PersistenciaException;
import desenvolvimento.mecaniqa_api.exception.RegraDeNegocioException;
import desenvolvimento.mecaniqa_api.mapper.PecaResultSetMapper;
import desenvolvimento.mecaniqa_api.mapper.ResultSetMapper;
import desenvolvimento.mecaniqa_api.model.Peca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class PecaRepository implements Repository<Peca, String> {

    private static final String INSERT =
            "INSERT INTO peca (codigo, codigo_barras, nome, fornecedor, quantidade_estoque, "
            + "preco_custo, preco_venda, data_cadastro, data_atualizacao, tamanho, cor, categoria) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT * FROM peca WHERE codigo = ?";
    private static final String SELECT_ALL = "SELECT * FROM peca ORDER BY nome";
    private static final String UPDATE =
            "UPDATE peca SET codigo_barras = ?, nome = ?, fornecedor = ?, quantidade_estoque = ?, "
            + "preco_custo = ?, preco_venda = ?, tamanho = ?, cor = ?, categoria = ?, "
            + "data_atualizacao = ? WHERE codigo = ?";
    private static final String DELETE = "DELETE FROM peca WHERE codigo = ?";

    private final ConnectionFactory connectionFactory;
    private final ResultSetMapper<Peca> mapper = new PecaResultSetMapper();

    public PecaRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Peca save(Peca peca) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            ps.setString(1, peca.getCodigo());
            ps.setString(2, peca.getCodigoBarras());
            ps.setString(3, peca.getNome());
            ps.setString(4, peca.getFornecedor());
            ps.setInt(5, peca.getQuantidadeEstoque());
            ps.setBigDecimal(6, peca.getPrecoCusto());
            ps.setBigDecimal(7, peca.getPrecoVenda());
            ps.setTimestamp(8, Timestamp.valueOf(peca.getDataCadastro()));
            ps.setTimestamp(9, Timestamp.valueOf(peca.getDataAtualizacao()));
            ps.setString(10, peca.getTamanho());
            ps.setString(11, peca.getCor());
            ps.setString(12, peca.getCategoria().name());
            ps.executeUpdate();
            return peca;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao salvar a peça.", e);
        }
    }

    @Override
    public Optional<Peca> findById(String codigo) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapper.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar a peça.", e);
        }
    }

    @Override
    public List<Peca> findAll() {
        List<Peca> pecas = new ArrayList<>();
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                pecas.add(mapper.map(rs));
            }
            return pecas;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar as peças.", e);
        }
    }

    @Override
    public boolean update(String codigo, Peca peca) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1, peca.getCodigoBarras());
            ps.setString(2, peca.getNome());
            ps.setString(3, peca.getFornecedor());
            ps.setInt(4, peca.getQuantidadeEstoque());
            ps.setBigDecimal(5, peca.getPrecoCusto());
            ps.setBigDecimal(6, peca.getPrecoVenda());
            ps.setString(7, peca.getTamanho());
            ps.setString(8, peca.getCor());
            ps.setString(9, peca.getCategoria().name());
            ps.setTimestamp(10, Timestamp.valueOf(peca.getDataAtualizacao()));
            ps.setString(11, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao atualizar a peça.", e);
        }
    }

    @Override
    public boolean deleteById(String codigo) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE)) {
            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RegraDeNegocioException(
                    "A peça está em uso em pedidos e não pode ser excluída.");
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao excluir a peça.", e);
        }
    }
}
