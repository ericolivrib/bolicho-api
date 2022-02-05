package bolicho.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    private int id;
    private Cliente cliente;
    private List<Item> itens;
    private LocalEntrega localEntrega;
    private Date dataPedido;
    private Date dataEntrega;
    private Date dataFinalizado;
    private double total;
    private Status status;
}
