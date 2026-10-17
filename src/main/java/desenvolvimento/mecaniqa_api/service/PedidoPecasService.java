package desenvolvimento.mecaniqa_api.service;

import desenvolvimento.mecaniqa_api.dto.ItemPedidoDTO;
import desenvolvimento.mecaniqa_api.dto.PedidoPecasDTO;
import desenvolvimento.mecaniqa_api.exception.DadosInvalidosException;
import desenvolvimento.mecaniqa_api.exception.PecaNaoEncontradaException;
import desenvolvimento.mecaniqa_api.exception.PedidoPecasNaoEncontradoException;
import desenvolvimento.mecaniqa_api.exception.RegraDeNegocioException;
import desenvolvimento.mecaniqa_api.mapper.PedidoPecasMapper;
import desenvolvimento.mecaniqa_api.model.ItemPedido;
import desenvolvimento.mecaniqa_api.model.Peca;
import desenvolvimento.mecaniqa_api.model.PedidoPecas;
import desenvolvimento.mecaniqa_api.repository.PecaRepository;
import desenvolvimento.mecaniqa_api.repository.PedidoPecasRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PedidoPecasService {

    private final PedidoPecasRepository pedidoRepository;
    private final PecaRepository pecaRepository;

    public PedidoPecasService(PedidoPecasRepository pedidoRepository,
                              PecaRepository pecaRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pecaRepository = pecaRepository;
    }

    public PedidoPecasDTO criar() {
        PedidoPecas pedido = pedidoRepository.save(new PedidoPecas(null));
        return paraDTO(pedido);
    }

    public List<PedidoPecasDTO> listar() {
        return pedidoRepository.findAll().stream().map(this::paraDTO).toList();
    }

    public PedidoPecasDTO buscarPorId(Long id) {
        return paraDTO(buscarEntidade(id));
    }

    public PedidoPecasDTO adicionarItem(Long id, ItemPedidoDTO dto) {
        if (dto == null
                || dto.getCodigoPeca() == null
                || dto.getCodigoPeca().isBlank()
                || dto.getQuantidade() <= 0) {
            throw new DadosInvalidosException(
                    "Informe codigoPeca e uma quantidade maior que zero.");
        }
        PedidoPecas pedido = buscarEntidade(id);
        Peca peca = pecaRepository.findById(dto.getCodigoPeca())
                .orElseThrow(() -> new PecaNaoEncontradaException(dto.getCodigoPeca()));
        try {
            pedido.adicionarItem(new ItemPedido(peca, dto.getQuantidade()));
        } catch (IllegalStateException e) {
            throw new RegraDeNegocioException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DadosInvalidosException(e.getMessage());
        }
        pedidoRepository.update(id, pedido);
        return paraDTO(pedido);
    }

    public PedidoPecasDTO atualizarStatus(Long id, PedidoPecasDTO dto) {
        if (dto == null || dto.getStatus() == null) {
            throw new DadosInvalidosException("O status é obrigatório.");
        }
        PedidoPecas pedido = buscarEntidade(id);
        pedido.setStatus(dto.getStatus());
        pedidoRepository.update(id, pedido);
        return paraDTO(pedido);
    }

    public void excluir(Long id) {
        if (!pedidoRepository.deleteById(id)) {
            throw new PedidoPecasNaoEncontradoException(id);
        }
    }

    private PedidoPecas buscarEntidade(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoPecasNaoEncontradoException(id));
    }

    private PedidoPecasDTO paraDTO(PedidoPecas pedido) {
        PedidoPecasDTO dto = PedidoPecasMapper.paraDTO(pedido);
        dto.setValorTotal(calcularValorTotal(pedido));
        return dto;
    }

    /** Soma preço de venda x quantidade de cada item do pedido. */
    private BigDecimal calcularValorTotal(PedidoPecas pedido) {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemPedido item : pedido.getItens()) {
            BigDecimal subtotal = item.getPeca().getPrecoVenda()
                    .multiply(BigDecimal.valueOf(item.getQuantidade()));
            total = total.add(subtotal);
        }
        return total;
    }
}
