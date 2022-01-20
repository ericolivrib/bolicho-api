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
        return this.pedidoDAO.recuperar();
    }

    public boolean cadastrarPedido(Pedido p) {

        p.getLocalEntrega().setId(this.enderecoDAO.incluir(p.getLocalEntrega()));

        if (p.getLocalEntrega().getId() != 0) {
            p.setId(this.pedidoDAO.incluir(p));

            if (p.getId() != 0) {
                for (Item i : p.getItens()) {
                    if (!this.itemDAO.incluir(i, p.getId())) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

    public boolean deletarPedido(Pedido pedido) {

        boolean isDeletado = false;

        if (this.itemDAO.deletarPorIdPedido(pedido.getId())) {
            if (this.pedidoDAO.deletar(pedido.getId())) {
                if (this.enderecoDAO.deletar(pedido.getLocalEntrega().getId())) {
                    isDeletado = true;
                }
            }
        }

        return isDeletado;
    }

    public boolean alterarStatusPedido(int idPedido, String novoStatus, LocalDate dataFinalizado) {
        Pedido p = new Pedido();
        p.setId(idPedido);
        p.setStatus(novoStatus);
        p.setDataFinalizado(dataFinalizado);

        return this.pedidoDAO.atualizarStatus(p);
    }
}
