package bolicho.service;

import bolicho.model.Produto;
import bolicho.dao.ProdutoDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoDAO produtoDAO;

    public ProdutoService() {
        this.produtoDAO = new ProdutoDAO();
    }

    public List<Produto> getProdutos() {
        return this.produtoDAO.recuperar();
    }

    public boolean cadastrarProduto(Produto p) {
        return this.produtoDAO.incluir(p);
    }

    public boolean atualizarProduto(Produto p) {
        return this.produtoDAO.atualizar(p);
    }

    public boolean atualizarEstoqueProduto(int id, int qtd) {
        Produto p = new Produto();
        p.setId(id);
        p.setQtdEstoque(qtd);

        return this.produtoDAO.atualizarEstoque(p);
    }

    public boolean deletarProduto(int id) {
        return this.produtoDAO.arquivar(id);
    }
}
