package bolicho.service;

import bolicho.dao.PedidoDAO;
import bolicho.model.Pedido;
import bolicho.model.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoDAO dao = new PedidoDAO();

    public List<Pedido> buscar() {
        return this.dao.buscar();
    }

    public Pedido incluir(Pedido pedido) {
        return this.dao.incluir(pedido);
    }

    public ResponseEntity<?> deletar(int id) {
        if (this.dao.deletar(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Pedido> alterarStatus(int id, Status status, Date dataFinalizado) {
        if (this.dao.atualizarStatus(id, status, dataFinalizado)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
