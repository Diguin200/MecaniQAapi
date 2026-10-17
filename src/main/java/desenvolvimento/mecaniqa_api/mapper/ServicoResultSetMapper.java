package desenvolvimento.mecaniqa_api.mapper;

import desenvolvimento.mecaniqa_api.model.Servico;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ServicoResultSetMapper implements ResultSetMapper<Servico> {

    @Override
    public Servico map(ResultSet rs) throws SQLException {
        Servico servico = new Servico();
        servico.setCodigo(rs.getString("codigo"));
        servico.setNome(rs.getString("nome"));
        servico.setDuracaoEstimadaMinutos(rs.getInt("duracao_estimada_minutos"));
        servico.setCustoTabela(rs.getBigDecimal("custo_tabela"));
        servico.setDataCadastro(paraDataHora(rs.getTimestamp("data_cadastro")));
        servico.setDataAtualizacao(paraDataHora(rs.getTimestamp("data_atualizacao")));
        return servico;
    }

    private static LocalDateTime paraDataHora(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
