package br.ufsm.csi.poow2.bolicho.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cliente {

    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private boolean ativo;
}
