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

    private final ClienteService clienteService;

    public ClienteController() {
        this.clienteService = new ClienteService();
    }

    @GetMapping("/")
    public List<Cliente> getClientes() {
        return this.clienteService.getClientes();
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastrarCliente(@RequestBody Cliente c) {

        if (this.clienteService.cadastrarCliente(c)) {
            return new ResponseEntity<>(c, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Falha ao cadastrar cliente!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{idCliente}/atualizar")
    public ResponseEntity<Object> atualizarCliente(@RequestBody Cliente c) {

        if (this.clienteService.atualizarCliente(c)) {
            return new ResponseEntity<>(c, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Falha ao atualizar cliente!", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{idCliente}/deletar")
    public ResponseEntity<Object> deletarCliente(@PathVariable int idCliente) {

        if (this.clienteService.deletarCliente(idCliente)) {
            return new ResponseEntity<>("Cliente deletado!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Falha ao deletar cliente!", HttpStatus.BAD_REQUEST);
        }
    }
}
