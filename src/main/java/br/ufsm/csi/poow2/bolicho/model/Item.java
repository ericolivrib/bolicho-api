package br.ufsm.csi.poow2.bolicho.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Item {

    private int id;
    private Produto produto;
    private int quantidade;
    private BigDecimal subtotal;
    private LocalDate dataValidade;
}
