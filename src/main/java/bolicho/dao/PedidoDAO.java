package bolicho.dao;

import bolicho.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    private final List<Pedido> pedidos = new ArrayList<>();

    public PedidoDAO() {

        pedidos.add(new Pedido(
                1,
                "0001",
                new ClienteDAO().getClienteById(1),
                new ItemDAO().getItens(),
                new EnderecoDAO().getEnderecoByID(1),
                LocalDate.of(2022, 1, 16),
                LocalDate.of(2022, 1, 16),
                null,
                BigDecimal.valueOf(27.00),
                "Em andamento"
        ));
    }

    public List<Pedido> getPedidos() {
        return this.pedidos;
    }

    public Pedido getPedidoById(int idPedido) {

        for (Pedido p : this.pedidos) {
            if (idPedido == p.getId()) {
                return p;
            }
        }

        return null;
    }

    public int adicionarPedido(Pedido p) {
        p.setId(this.pedidos.size() + 1);
        this.pedidos.add(p);
        return p.getId();
    }

    public boolean deletarPedido(int idPedido) {

        for (Pedido p : this.pedidos) {
            if (idPedido == p.getId()) {
                this.pedidos.remove(p);
                return true;
            }
        }

        return false;
    }

    public boolean atualizarStatusPedido(Pedido p) {

        for (Pedido pedido : this.pedidos) {
            if (p.getId() == pedido.getId()) {
                pedido.setStatus(p.getStatus());

                if (p.getStatus().equals("Finalizado")) {
                    pedido.setDataFinalizado(p.getDataFinalizado());
                }

                this.pedidos.set(this.pedidos.indexOf(pedido), pedido);
                return true;
            }
        }

        return false;
    }
}
