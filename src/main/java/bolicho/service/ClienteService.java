package bolicho.service;

import bolicho.dao.ClienteDAO;
import bolicho.model.Cliente;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteDAO dao = new ClienteDAO();

    public List<Cliente> buscar() {
        return this.dao.buscar();
    }

    public Cliente incluir(Cliente cliente) {
        return this.dao.incluir(cliente);
    }

    public ResponseEntity<Cliente> atualizar(Cliente cliente) {
        if (this.dao.atualizar(cliente)) {
            return ResponseEntity.ok().body(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> deletar(int id) {
        if (this.dao.desativar(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
