package bolicho.controller;

import bolicho.model.Cliente;

import bolicho.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clientes")
public class ClienteController {

    private final ClienteService service = new ClienteService();

    @GetMapping
    public List<Cliente> getClientes() {
        return this.service.buscar();
    }

    @GetMapping("/{id}")
    public Cliente getCliente(@PathVariable int id) {
        return this.service.buscarCurso(id);
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente cadastrarCliente(@RequestBody Cliente cliente) {
        return this.service.incluir(cliente);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Cliente> atualizarCliente(@RequestBody Cliente cliente) {
        return this.service.atualizar(cliente);
    }

    @DeleteMapping("/desativar/{id}")
    public ResponseEntity<?> deletarCliente(@PathVariable int id) {
        return this.service.deletar(id);
    }
}
