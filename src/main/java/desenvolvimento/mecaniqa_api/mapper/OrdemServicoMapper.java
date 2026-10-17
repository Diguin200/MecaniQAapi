
package desenvolvimento.mecaniqa_api.mapper;

import desenvolvimento.mecaniqa_api.dto.OrdemServicoDTO;
import desenvolvimento.mecaniqa_api.model.OrdemServico;
import desenvolvimento.mecaniqa_api.builder.OrdemServicoBuilder;

public class OrdemServicoMapper {

    public static OrdemServicoDTO paraDTO(OrdemServico ordem) {
        return new OrdemServicoDTO(
                ordem.getId(),
                ordem.getDescricao(),
                ordem.getNomeCliente(),
                ordem.getStatus()
        );
    }

    public static OrdemServico paraEntidade(OrdemServicoDTO dto) {
        return new OrdemServicoBuilder()
                .id(dto.getId())
                .descricao(dto.getDescricao())
                .nomeCliente(dto.getNomeCliente())
                .status(dto.getStatus() == null
                        ? desenvolvimento.mecaniqa_api.model.StatusOrdemServico.ABERTO
                        : dto.getStatus())
                .build();
    }
}
