package bolicho.controller;

import bolicho.model.Status;
import bolicho.service.PedidoService;
import bolicho.model.Pedido;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("pedidos")
public class PedidoController {

    private final PedidoService service = new PedidoService();

    @GetMapping
    public List<Pedido> getPedidos() {
        return this.service.buscar();
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido cadastrarPedido(@RequestBody Pedido pedido) {
        return this.service.incluir(pedido);
    }

    @DeleteMapping("/{id}/deletar")
    public ResponseEntity<?> deletarPedido(@PathVariable int id) {
        return this.service.deletar(id);
    }

    @PutMapping("/{id}/alterar-status")
    public ResponseEntity<Pedido> alterarStatus(@PathVariable int id,
                                                @RequestParam String status,
                                                @RequestParam Date dataFinalizado) {
        return this.service.alterarStatus(id, Status.valueOf(status), dataFinalizado);
    }
}
