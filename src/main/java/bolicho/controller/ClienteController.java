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

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente cadastrarCliente(@RequestBody Cliente cliente) {
        return this.service.incluir(cliente);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Cliente> atualizarCliente(@RequestBody Cliente cliente) {
        return this.service.atualizar(cliente);
    }

    @DeleteMapping("/{id}/desativar")
    public ResponseEntity<?> deletarCliente(@PathVariable int id) {
        return this.service.deletar(id);
    }
}
