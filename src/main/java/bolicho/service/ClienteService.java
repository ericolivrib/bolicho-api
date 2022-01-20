package bolicho.service;

import bolicho.dao.ClienteDAO;
import bolicho.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }

    public List<Cliente> getClientes() {
        return this.clienteDAO.recuperar();
    }

    public boolean cadastrarCliente(Cliente c) {
        return this.clienteDAO.incluir(c);
    }

    public boolean atualizarCliente(Cliente c) {
        return this.clienteDAO.atualizar(c);
    }

    public boolean deletarCliente(int id) {
        return this.clienteDAO.desativar(id);
    }
}
