package bolicho.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produto {

    private int id;
    private String descricao;
    private BigDecimal precoUnitario;
    private String unidadeMedida;
    private int qtdEstoque;
}
