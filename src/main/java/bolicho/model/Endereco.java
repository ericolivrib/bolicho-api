package bolicho.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {

    private int id;
    private String cep;
    private String bairro;
    private String logradouro;
    private int numero;
    private String complemento;
    private String pontoReferencia;
}
