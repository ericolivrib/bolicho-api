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

    private final ProdutoService produtoService;

    public ProdutoController() {
        this.produtoService = new ProdutoService();
    }

    @GetMapping("/")
    public List<Produto> getProdutos() {
        return this.produtoService.getProdutos();
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastrarProduto(@RequestBody Produto p) {

        if (this.produtoService.cadastrarProduto(p)) {
            return new ResponseEntity<>(p, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Falha ao cadastrar produto", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/atualizar/{idProduto}")
    public ResponseEntity<Object> atualizarProduto(@RequestBody Produto p) {

        if (this.produtoService.atualizarProduto(p)) {
            return new ResponseEntity<>(p, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Falha ao atualizar produto!", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deletar/{idProduto}")
    public ResponseEntity<Object> deletarProduto(@PathVariable int idProduto) {

        if (this.produtoService.deletarProduto(idProduto)) {
            return new ResponseEntity<>("Produto deletado!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Falha ao deletar produto!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/atualizar-estoque/{idProduto}")
    public ResponseEntity<Object> aumentarEstoque(@PathVariable int idProduto,
                                                  @RequestParam int quantidade) {

        if (this.produtoService.atualizarEstoqueProduto(idProduto, quantidade)) {
            return new ResponseEntity<>("Estoque do produto atualizado!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Falha ao atualizar o estoque do produto", HttpStatus.BAD_REQUEST);
        }
    }
}
