package br.ufsm.csi.poow2.bolicho.model;

import lombok.Data;

@Data
public class Cliente {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private boolean ativo;
    private Endereco endereco;
}
