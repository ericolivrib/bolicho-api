package bolicho.controller;

import bolicho.model.Produto;
import bolicho.service.ProdutoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    private final ProdutoService service = new ProdutoService();;

    @GetMapping
    public List<Produto> getProdutos() {
        return this.service.buscar();
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public Produto cadastrarProduto(@RequestBody Produto produto) {
        return this.service.incluir(produto);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Produto> atualizarProduto(@RequestBody Produto produto) {
        return this.service.atualizar(produto);
    }

    @PutMapping("/atualizar-estoque/{id}")
    public ResponseEntity<Produto> aumentarEstoque(@PathVariable int id,
                                                  @RequestParam int quantidade) {
        return this.service.atualizarEstoque(id, quantidade);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarProduto(@PathVariable int id) {
        return this.service.deletar(id);
    }

}
