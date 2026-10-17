
package desenvolvimento.mecaniqa_api.controller;

import desenvolvimento.mecaniqa_api.dto.ItemPedidoDTO;
import desenvolvimento.mecaniqa_api.dto.PedidoPecasDTO;
import desenvolvimento.mecaniqa_api.mapper.PedidoPecasMapper;
import desenvolvimento.mecaniqa_api.model.ItemPedido;
import desenvolvimento.mecaniqa_api.model.Peca;
import desenvolvimento.mecaniqa_api.model.PedidoPecas;
import desenvolvimento.mecaniqa_api.model.StatusPedidoPecas;
import desenvolvimento.mecaniqa_api.repository.PecaRepository;
import desenvolvimento.mecaniqa_api.repository.PedidoPecasRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos-pecas")
public class PedidoPecasController {

    private final PedidoPecasRepository pedidoRepository =
            PedidoPecasRepository.getInstance();

    private final PecaRepository pecaRepository =
            PecaRepository.getInstance();

    @PostMapping
    public ResponseEntity<PedidoPecasDTO> criar() {
        PedidoPecas pedido = pedidoRepository.salvar();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PedidoPecasMapper.paraDTO(pedido));
    }

    @GetMapping
    public ResponseEntity<List<PedidoPecasDTO>> listar() {
        List<PedidoPecasDTO> pedidos = pedidoRepository.listarTodos()
                .stream()
                .map(PedidoPecasMapper::paraDTO)
                .toList();

        return ResponseEntity.ok(pedidos);
    }

    @PostMapping("/{id}/itens")
    public ResponseEntity<PedidoPecasDTO> adicionarItem(
            @PathVariable Long id,
            @RequestBody ItemPedidoDTO dto) {

        if (dto.getCodigoPeca() == null ||
                dto.getCodigoPeca().isBlank() ||
                dto.getQuantidade() <= 0) {
            return ResponseEntity.badRequest().build();
        }

        var pedidoOptional = pedidoRepository.buscarPorId(id);

        if (pedidoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var pecaOptional = pecaRepository.buscarPorCodigo(dto.getCodigoPeca());

        if (pecaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            PedidoPecas pedido = pedidoOptional.get();
            Peca peca = pecaOptional.get();

            pedido.adicionarItem(new ItemPedido(peca, dto.getQuantidade()));

            return ResponseEntity.ok(PedidoPecasMapper.paraDTO(pedido));

        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoPecasDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestBody PedidoPecasDTO dto) {

        if (dto.getStatus() == null) {
            return ResponseEntity.badRequest().build();
        }

        var pedidoOptional = pedidoRepository.buscarPorId(id);

        if (pedidoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            PedidoPecas pedido = pedidoOptional.get();
            pedido.setStatus(dto.getStatus());

            return ResponseEntity.ok(PedidoPecasMapper.paraDTO(pedido));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
