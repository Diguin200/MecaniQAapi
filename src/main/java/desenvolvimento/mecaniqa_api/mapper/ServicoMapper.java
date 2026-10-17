
package desenvolvimento.mecaniqa_api.mapper;

import desenvolvimento.mecaniqa_api.dto.ServicoDTO;
import desenvolvimento.mecaniqa_api.model.Servico;

public class ServicoMapper {

    public static ServicoDTO paraDTO(Servico servico) {
        ServicoDTO dto = new ServicoDTO();

        dto.setCodigo(servico.getCodigo());
        dto.setNome(servico.getNome());
        dto.setDuracaoEstimadaMinutos(
                servico.getDuracaoEstimadaMinutos());
        dto.setCustoTabela(servico.getCustoTabela());
        dto.setDataCadastro(servico.getDataCadastro());
        dto.setDataAtualizacao(servico.getDataAtualizacao());

        return dto;
    }

    public static Servico paraEntidade(ServicoDTO dto) {
        Servico servico = new Servico();

        servico.setCodigo(dto.getCodigo());
        servico.setNome(dto.getNome());
        servico.setDuracaoEstimadaMinutos(
                dto.getDuracaoEstimadaMinutos());
        servico.setCustoTabela(dto.getCustoTabela());
        servico.setDataCadastro(dto.getDataCadastro());
        servico.setDataAtualizacao(dto.getDataAtualizacao());

        return servico;
    }
}
