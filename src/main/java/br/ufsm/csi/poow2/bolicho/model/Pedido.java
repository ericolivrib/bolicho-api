package br.ufsm.csi.poow2.bolicho.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class Pedido {

    private int id;
    private Cliente cliente;
    private List<Item> itens;
    private Endereco enderecoEntrega;
    private LocalDate dataPedido;
    private LocalDate dataEntrega;
    private LocalDate dataFinalizado;
    private BigDecimal total;
    private Status status;

    private enum Status {
        EM_ANDAMENTO("Em andamento"),
        CANCELADO("Cancelado"),
        FINALIZADO("Finalizado"),
        ATRASADO("Atrasado");

        Status(String status) {
        }
    }
}
