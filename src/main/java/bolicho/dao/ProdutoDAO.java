package bolicho.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import bolicho.model.Produto;

public class ProdutoDAO {

    private final List<Produto> produtos = new ArrayList<>();

    public ProdutoDAO() {
        produtos.add(new Produto(1, "Queijo Colonial", BigDecimal.valueOf(27.00), "Kg", 10));
        produtos.add(new Produto(2, "Chimia", BigDecimal.valueOf(3.50), "Unidade", 11));
        produtos.add(new Produto(3, "Licor", BigDecimal.valueOf(6.00), "Unidade", 10));
    }

    public List<Produto> getProdutos() {
        return this.produtos;
    }

    public Produto getProdutoById(int idProduto) {

        for (Produto p : this.produtos) {
            if (idProduto == p.getId()) {
                return p;
            }
        }

        return null;
    }

    public boolean adicionarProduto(Produto p) {
        p.setId(this.produtos.size() + 1);
        return this.produtos.add(p);
    }

    public boolean atualizarProduto(Produto p) {

        for (Produto produto : this.produtos) {
            if (p.getId() == produto.getId()) {
                this.produtos.set(this.produtos.indexOf(produto), p);
                return true;
            }
        }

        return false;
    }

    public boolean atualizarEstoqueProduto(Produto p) {

        for (Produto produto : this.produtos) {
            if (p.getId() == produto.getId()) {
                produto.setQtdEstoque(p.getQtdEstoque() + produto.getQtdEstoque());
                this.produtos.set(this.produtos.indexOf(produto), produto);
                return true;
            }
        }

        return false;
    }

    public boolean deletarProduto(int idProduto) {
        return this.produtos.removeIf(p -> idProduto == p.getId());
    }
}
