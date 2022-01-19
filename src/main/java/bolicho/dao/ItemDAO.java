package bolicho.dao;

import bolicho.model.Item;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    private final List<Item> itens = new ArrayList<>();

    public ItemDAO() {
        this.itens.add(new Item(
                1,
                new ProdutoDAO().getProdutoById(1),
                2,
                BigDecimal.valueOf(52.00),
                LocalDate.of(2022, 7, 20)));
    }

    public List<Item> getItens() {
        return this.itens;
    }

    public Item getItemById(int idItem) {

        for (Item i : this.itens) {
            if (idItem == i.getId()) {
                return i;
            }
        }

        return null;
    }

    public boolean adicionarItem(Item item, int idPedido) {
        item.setId(this.itens.size() + 1);
        return this.itens.add(item);
    }

    public boolean removerItem(int idItem) {
        return this.itens.removeIf(i -> idItem == i.getId());
    }
}
