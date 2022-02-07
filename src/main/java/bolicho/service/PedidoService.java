package bolicho.service;

import bolicho.dao.PedidoDAO;
import bolicho.model.Pedido;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoDAO dao = new PedidoDAO();

    public List<Pedido> buscar() {
        return this.dao.buscarPedidos();
    }

    public Pedido incluir(Pedido pedido) {
        return this.dao.incluir(pedido);
    }

    public ResponseEntity<Pedido> alterarStatus(int id, String status) {
        if (this.dao.atualizarStatus(id, status)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> deletar(int id) {
        if (this.dao.deletar(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
