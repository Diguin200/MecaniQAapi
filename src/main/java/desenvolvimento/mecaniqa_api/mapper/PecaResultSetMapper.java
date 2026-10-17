package desenvolvimento.mecaniqa_api.mapper;

import desenvolvimento.mecaniqa_api.model.CategoriaPeca;
import desenvolvimento.mecaniqa_api.model.Peca;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PecaResultSetMapper implements ResultSetMapper<Peca> {

    @Override
    public Peca map(ResultSet rs) throws SQLException {
        Peca peca = new Peca();
        peca.setCodigo(rs.getString("codigo"));
        peca.setCodigoBarras(rs.getString("codigo_barras"));
        peca.setNome(rs.getString("nome"));
        peca.setFornecedor(rs.getString("fornecedor"));
        peca.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
        peca.setPrecoCusto(rs.getBigDecimal("preco_custo"));
        peca.setPrecoVenda(rs.getBigDecimal("preco_venda"));
        peca.setDataCadastro(paraDataHora(rs.getTimestamp("data_cadastro")));
        peca.setDataAtualizacao(paraDataHora(rs.getTimestamp("data_atualizacao")));
        peca.setTamanho(rs.getString("tamanho"));
        peca.setCor(rs.getString("cor"));
        String categoria = rs.getString("categoria");
        peca.setCategoria(categoria == null ? null : CategoriaPeca.valueOf(categoria));
        return peca;
    }

    private static LocalDateTime paraDataHora(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
