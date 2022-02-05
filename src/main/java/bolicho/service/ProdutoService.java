package bolicho.service;

import bolicho.model.Produto;
import bolicho.dao.ProdutoDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoDAO dao = new ProdutoDAO();

    public List<Produto> buscar() {
        return this.dao.buscar();
    }

    public Produto incluir(Produto produto) {
        return this.dao.incluir(produto);
    }

    public ResponseEntity<Produto> atualizar(Produto produto) {
        if (this.dao.atualizar(produto)) {
            return ResponseEntity.ok().body(produto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Produto> atualizarEstoque(int id, int quantidade) {
        if (this.dao.atualizarEstoque(id, quantidade)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> deletar(int id) {
        if (this.dao.arquivar(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
