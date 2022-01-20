package bolicho.dao;

import bolicho.model.*;
import bolicho.util.ConexaoDBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public List<Pedido> recuperar() {

        List<Pedido> pedidos = new ArrayList<>();

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "SELECT * FROM pedido;";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                Pedido p = new Pedido(
                        result.getInt("id"),
                        new ClienteDAO().recuperarPeloId(result.getInt("cliente_id")),
                        new ItemDAO().recuperarPorIdPedido(result.getInt("id")),
                        new EnderecoDAO().recuperarPeloId(result.getInt("endereco_id")),
                        result.getDate("data_pedido").toLocalDate(),
                        result.getDate("data_entrega").toLocalDate(),
                        result.getDate("data_finalizado").toLocalDate(),
                        result.getBigDecimal("total"),
                        result.getString("status")
                );

                pedidos.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }

    public Pedido recuperarPeloId(int id) {
        return null;
    }

    public int incluir(Pedido pedido) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "INSERT INTO pedido (cliente_id, endereco_id, data_pedido, data_entrega, total, status) " +
                    "VALUES (?, ?, ?, ?, ?, 'Em andamento')";

            PreparedStatement statement = connection.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setInt(1, pedido.getCliente().getId());
            statement.setInt(2, pedido.getEnderecoEntrega().getId());
            statement.setDate(3, Date.valueOf(pedido.getDataPedido()));
            statement.setDate(4, Date.valueOf(pedido.getDataEntrega()));
            statement.setBigDecimal(5, pedido.getTotal());

            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            result.next();

            if (result.getInt(1) > 0) {
                return result.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public boolean deletar(int id) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "DELETE FROM pedido WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            return statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarStatus(Pedido pedido) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            if (pedido.getDataFinalizado() != null && pedido.getStatus().equals("Finalizado")) {
                String sql = "UPDATE pedido SET status=?, data_finalizado=? WHERE id=?";
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setString(1, pedido.getStatus());
                statement.setDate(2, Date.valueOf(pedido.getDataFinalizado()));
                statement.setInt(3, pedido.getId());
                statement.executeUpdate();
            } else {
                String sql = "UPDATE pedido SET status=? WHERE id=?";
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setString(1, pedido.getStatus());
                statement.setInt(2, pedido.getId());
                statement.executeUpdate();
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

}
