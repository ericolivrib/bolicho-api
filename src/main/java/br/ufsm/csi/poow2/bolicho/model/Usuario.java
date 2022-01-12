package br.ufsm.csi.poow2.bolicho.model;

import lombok.Data;

@Data
public class Usuario {
    private int id;
    private String email;
    private String senha;
}
