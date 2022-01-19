package bolicho.service;

import bolicho.dao.PedidoDAO;
import bolicho.model.Item;
import bolicho.dao.EnderecoDAO;
import bolicho.dao.ItemDAO;
import bolicho.model.Pedido;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoDAO pedidoDAO;
    private final ItemDAO itemDAO;
    private final EnderecoDAO enderecoDAO;

    public PedidoService() {
        this.pedidoDAO = new PedidoDAO();
        this.itemDAO = new ItemDAO();
        this.enderecoDAO = new EnderecoDAO();
    }

    public List<Pedido> getPedidos() {
        return this.pedidoDAO.getPedidos();
    }

    public boolean cadastrarPedido(Pedido p) {

        boolean itensForamAdicionados = true;

        p.getEnderecoEntrega().setId(this.enderecoDAO.cadastrarEndereco(p.getEnderecoEntrega()));

        if (p.getEnderecoEntrega().getId() != 0) {
            p.setId(this.pedidoDAO.adicionarPedido(p));
            if (p.getId() != 0) {
                for (Item i : p.getItens()) {
                    this.itemDAO.adicionarItem(i, p.getId());

                    if (!this.itemDAO.adicionarItem(i, p.getId())) {
                        itensForamAdicionados = false;
                        break;
                    }
                }

                return itensForamAdicionados;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean deletarPedido(Pedido p) {

        boolean itensForamRemovidos = true;

        for (Item i : p.getItens()) {
            if (!this.itemDAO.removerItem(i.getId())) {
                itensForamRemovidos = false;
                break;
            }
        }

        if (itensForamRemovidos) {
            if (this.pedidoDAO.deletarPedido(p.getId())) {
                if (this.enderecoDAO.deletarEndereco(p.getEnderecoEntrega().getId())) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean alterarStatusPedido(int idPedido, String novoStatus, LocalDate dataFinalizado) {

        Pedido p = new Pedido();
        p.setId(idPedido);
        p.setStatus(novoStatus);
        p.setDataFinalizado(dataFinalizado);

        return this.pedidoDAO.atualizarStatusPedido(p);
    }
}
