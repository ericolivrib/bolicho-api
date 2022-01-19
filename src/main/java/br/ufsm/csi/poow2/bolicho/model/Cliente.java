package br.ufsm.csi.poow2.bolicho.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private boolean ativo;
}
