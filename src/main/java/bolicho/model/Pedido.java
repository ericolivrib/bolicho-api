package bolicho.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    private int id;
    private Cliente cliente;
    private List<Item> itens;
    private Endereco enderecoEntrega;
    private LocalDate dataPedido;
    private LocalDate dataEntrega;
    private LocalDate dataFinalizado;
    private BigDecimal total;
    private String status;
}
