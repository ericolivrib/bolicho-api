package br.ufsm.csi.poow2.bolicho.model;

import lombok.Data;

@Data
public class Endereco {

    private int id;
    private String cep;
    private String bairro;
    private String logradouro;
    private int numero;
    private String complemento;
    private String pontoReferencia;
}
