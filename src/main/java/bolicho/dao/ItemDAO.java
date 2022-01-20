package bolicho.dao;

import bolicho.model.Item;
import bolicho.util.ConexaoDBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    public List<Item> recuperarPorIdPedido(int idPedido) {

        List<Item> itens = new ArrayList<>();

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "SELECT * FROM item WHERE pedido_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idPedido);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Item i = new Item(
                        result.getInt("id"),
                        new ProdutoDAO().recuperarPeloId(result.getInt("produto_id")),
                        result.getInt("quantidade"),
                        result.getBigDecimal("subtotal"),
                        result.getDate("data_validade").toLocalDate()
                );

                itens.add(i);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itens;
    }

    public Item recuperarPeloId(int id) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "SELECT * FROM item WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new Item(
                        result.getInt("id"),
                        new ProdutoDAO().recuperarPeloId(result.getInt("produto_id")),
                        result.getInt("quantidade"),
                        result.getBigDecimal("subtotal"),
                        result.getDate("data_validade").toLocalDate()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean incluir(Item item, int idPedido) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "INSERT INTO item (produto_id, pedido_id, subtotal, quantidade, data_validade) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, item.getProduto().getId());
            statement.setInt(2, idPedido);
            statement.setBigDecimal(3, item.getSubtotal());
            statement.setInt(4, item.getQuantidade());
            statement.setDate(5, Date.valueOf(item.getDataValidade()));

            return statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean deletar(int id) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "DELETE FROM item WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            return statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deletarPorIdPedido(int idPedido) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "DELETE FROM item WHERE pedido_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idPedido);

            return statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
