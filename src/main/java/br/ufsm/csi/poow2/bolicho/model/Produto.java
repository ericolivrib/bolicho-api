package br.ufsm.csi.poow2.bolicho.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Produto {

    private int id;
    private String descricao;
    private BigDecimal precoUnitario;
    private String unidadeMedida;
    private int qtdEstoque;
}
