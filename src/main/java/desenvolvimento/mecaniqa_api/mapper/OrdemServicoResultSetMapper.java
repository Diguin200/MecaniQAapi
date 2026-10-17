package desenvolvimento.mecaniqa_api.mapper;

import desenvolvimento.mecaniqa_api.model.OrdemServico;
import desenvolvimento.mecaniqa_api.model.StatusOrdemServico;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdemServicoResultSetMapper implements ResultSetMapper<OrdemServico> {

    @Override
    public OrdemServico map(ResultSet rs) throws SQLException {
        return new OrdemServico(
                rs.getLong("id"),
                rs.getString("descricao"),
                rs.getString("nome_cliente"),
                StatusOrdemServico.valueOf(rs.getString("status")));
    }
}
