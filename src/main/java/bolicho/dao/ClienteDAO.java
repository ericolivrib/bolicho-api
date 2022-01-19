package bolicho.dao;

import bolicho.model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private final List<Cliente> clientes = new ArrayList<>();

    public ClienteDAO() {
        clientes.add(new Cliente(1, "Fulano", "fulano@email", "(99) 99999-9999", "999.999.999-99", true));
        clientes.add(new Cliente(2, "Beltrano", "beltrano@email", "(88) 88888-8888", "888.888.888-88", true));
        clientes.add(new Cliente(3, "Ciclano", "ciclano@email", "(77) 77777-7777", "777.777.777-77", true));
    }

    public List<Cliente> getClientes() {
        return this.clientes;
    }

    public Cliente getClienteById(int idCliente) {

        for (Cliente c : this.clientes) {
            if (idCliente == c.getId()) {
                return c;
            }
        }

        return null;
    }

    public boolean adicionarCliente(Cliente c) {
        c.setId(this.clientes.size() + 1);
        c.setAtivo(true);
        return this.clientes.add(c);
    }

    public boolean atualizarCliente(Cliente c) {

        for (Cliente cliente : this.clientes) {
            if (c.getId() == cliente.getId()) {
                this.clientes.set(this.clientes.indexOf(cliente), c);
                return true;
            }
        }

        return false;
    }

    public boolean deletarCliente(int idCliente) {

        return this.clientes.removeIf(c -> idCliente == c.getId());
    }
}
