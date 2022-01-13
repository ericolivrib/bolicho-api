package br.ufsm.csi.poow2.bolicho.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Produto {

    private int id;
    private String descricao;
    private BigDecimal precoUnitario;
    private String unidadeMedida;
    private int qtdEstoque;
}
