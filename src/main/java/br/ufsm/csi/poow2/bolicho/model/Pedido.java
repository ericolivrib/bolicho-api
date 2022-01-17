package br.ufsm.csi.poow2.bolicho.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class Pedido {

    private int id;
    private String numPedido;
    private Cliente cliente;
    private List<Item> itens;
    private Endereco enderecoEntrega;
    private LocalDate dataPedido;
    private LocalDate dataEntrega;
    private LocalDate dataFinalizado;
    private BigDecimal total;
    private String status;
}
