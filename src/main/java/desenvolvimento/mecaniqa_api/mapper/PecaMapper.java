
package desenvolvimento.mecaniqa_api.mapper;

import desenvolvimento.mecaniqa_api.dto.PecaDTO;
import desenvolvimento.mecaniqa_api.model.Peca;

public class PecaMapper {

    public static PecaDTO paraDTO(Peca peca) {
        PecaDTO dto = new PecaDTO();

        dto.setCodigo(peca.getCodigo());
        dto.setCodigoBarras(peca.getCodigoBarras());
        dto.setNome(peca.getNome());
        dto.setFornecedor(peca.getFornecedor());
        dto.setQuantidadeEstoque(peca.getQuantidadeEstoque());
        dto.setPrecoCusto(peca.getPrecoCusto());
        dto.setPrecoVenda(peca.getPrecoVenda());
        dto.setDataCadastro(peca.getDataCadastro());
        dto.setDataAtualizacao(peca.getDataAtualizacao());
        dto.setTamanho(peca.getTamanho());
        dto.setCor(peca.getCor());
        dto.setCategoria(peca.getCategoria());

        return dto;
    }

    public static Peca paraEntidade(PecaDTO dto) {
        Peca peca = new Peca();

        peca.setCodigo(dto.getCodigo());
        peca.setCodigoBarras(dto.getCodigoBarras());
        peca.setNome(dto.getNome());
        peca.setFornecedor(dto.getFornecedor());
        peca.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        peca.setPrecoCusto(dto.getPrecoCusto());
        peca.setPrecoVenda(dto.getPrecoVenda());
        peca.setDataCadastro(dto.getDataCadastro());
        peca.setDataAtualizacao(dto.getDataAtualizacao());
        peca.setTamanho(dto.getTamanho());
        peca.setCor(dto.getCor());
        peca.setCategoria(dto.getCategoria());

        return peca;
    }
}
