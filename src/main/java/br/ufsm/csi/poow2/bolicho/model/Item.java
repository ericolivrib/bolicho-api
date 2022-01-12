package br.ufsm.csi.poow2.bolicho.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Item {
    private int id;
    private Produto produto;
    private BigDecimal subtotal;
    private int quantidade;
    private LocalDate dataValidade;
}
