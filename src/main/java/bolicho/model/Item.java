package bolicho.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private int id;
    private Produto produto;
    private int quantidade;
    private BigDecimal subtotal;
    private LocalDate dataValidade;
}
