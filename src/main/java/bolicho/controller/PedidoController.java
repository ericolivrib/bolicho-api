package bolicho.controller;

import bolicho.service.PedidoService;
import bolicho.model.Pedido;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController() {
        this.pedidoService = new PedidoService();
    }

    @GetMapping("/")
    public List<Pedido> getPedidos() {
        return this.pedidoService.getPedidos();
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastrarPedido(@RequestBody Pedido p) {

        if (this.pedidoService.cadastrarPedido(p)) {
            return new ResponseEntity<>(p, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Falha ao cadastrar pedido", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{idPedido}/deletar")
    public ResponseEntity<Object> deletarPedido(@RequestBody Pedido p) {

        if (this.pedidoService.deletarPedido(p)) {
            return new ResponseEntity<>(p, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Falha ao deletar pedido!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{idPedido}/alterar-status")
    public ResponseEntity<Object> alterarStatus(@PathVariable int idPedido,
                                                @RequestParam String novoStatus,
                                                @RequestParam String dataFinalizado) {

        if (this.pedidoService.alterarStatusPedido(idPedido, novoStatus, LocalDate.parse(dataFinalizado))) {
            return new ResponseEntity<>("Status do pedido atualizado!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Falha ao atualizar o status do pedido!", HttpStatus.BAD_REQUEST);
        }
    }
}
