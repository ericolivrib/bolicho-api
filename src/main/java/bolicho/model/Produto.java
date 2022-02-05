package bolicho.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produto {

    private int id;
    private String descricao;
    private double precoUnitario;
    private String unidadeMedida;
    private double qtdEstoque;
    private boolean arquivado;
}
