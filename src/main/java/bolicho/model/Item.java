package bolicho.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private int id;
    private Produto produto;
    private double quantidade;
    private double subtotal;
    private Date dataValidade;
}
