package bolicho.dao;

import bolicho.model.*;
import bolicho.util.ConexaoDBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public List<Pedido> buscar() {
        List<Pedido> pedidos = new ArrayList<>();

        try (Connection connection = ConexaoDBUtil.getConnection()) {
            String sql = "SELECT * FROM pedido;";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                Pedido p = new Pedido(
                        result.getInt("id"),
                        new ClienteDAO().buscarPorId(result.getInt("cliente_id")),
                        this.buscarItensPorIdPedido(result.getInt("id")),
                        this.buscarLocalEntrega(result.getInt("endereco_id")),
                        result.getDate("data_pedido"),
                        result.getDate("data_entrega"),
                        null,
                        result.getDouble("total"),
                        result.getString("status")
                );

                if (result.getDate("data_finalizado") != null) {
                    p.setDataFinalizado(result.getDate("data_finalizado"));
                }

                pedidos.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }

    public Pedido buscarPorId(int id) {
        return null;
    }

    public List<Item> buscarItensPorIdPedido(int idPedido) {
        List<Item> itens = new ArrayList<>();

        try (Connection connection = ConexaoDBUtil.getConnection()) {
            String sql = "SELECT * FROM item WHERE pedido_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idPedido);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Item i = new Item(
                        result.getInt("id"),
                        new ProdutoDAO().buscarPorId(result.getInt("produto_id")),
                        result.getDouble("quantidade"),
                        result.getDouble("subtotal"),
                        result.getDate("data_validade")
                );

                itens.add(i);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itens;
    }

    public Item buscarItemPorId(int id) {
        try (Connection connection = ConexaoDBUtil.getConnection()) {
            String sql = "SELECT * FROM item WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new Item(
                        result.getInt("id"),
                        new ProdutoDAO().buscarPorId(result.getInt("produto_id")),
                        result.getDouble("quantidade"),
                        result.getDouble("subtotal"),
                        result.getDate("data_validade")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public LocalEntrega buscarLocalEntrega(int id) {
        try (Connection connection = ConexaoDBUtil.getConnection()) {
            String sql = "SELECT * FROM endereco WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new LocalEntrega(
                        result.getInt("id"),
                        result.getString("cep"),
                        result.getString("bairro"),
                        result.getString("logradouro"),
                        result.getInt("numero"),
                        result.getString("complemento"),
                        result.getString("ponto_referencia")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Pedido incluir(Pedido pedido) {

        LocalEntrega localEntrega = pedido.getLocalEntrega();
        localEntrega.setId(this.incluirLocalEntrega(localEntrega));

        if (localEntrega.getId() != 0) {

            try (Connection connection = ConexaoDBUtil.getConnection()) {

                connection.setAutoCommit(false);

                String sql = "INSERT INTO pedido (cliente_id, endereco_id, data_pedido, data_entrega, total, status) " +
                        "VALUES (?, ?, ?, ?, ?, 'Em andamento')";

                PreparedStatement statement = connection.prepareStatement(sql,
                        PreparedStatement.RETURN_GENERATED_KEYS);

                statement.setInt(1, pedido.getCliente().getId());
                statement.setInt(2, localEntrega.getId());
                statement.setDate(3, pedido.getDataPedido());
                statement.setDate(4, pedido.getDataEntrega());
                statement.setDouble(5, pedido.getTotal());

                statement.executeUpdate();
                ResultSet result = statement.getGeneratedKeys();
                result.next();

                if (result.getInt(1) > 0) {
                    this.incluirItens(pedido.getItens(), result.getInt(1));
                    connection.commit();
                    return pedido;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void incluirItens(List<Item> itens, int idPedido) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            connection.setAutoCommit(false);

            for (Item item : itens) {
                String sql = "INSERT INTO item (produto_id, pedido_id, subtotal, quantidade, data_validade) " +
                        "VALUES (?, ?, ?, ?, ?)";

                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setInt(1, item.getProduto().getId());
                statement.setInt(2, idPedido);
                statement.setDouble(3, item.getSubtotal());
                statement.setDouble(4, item.getQuantidade());
                statement.setDate(5, item.getDataValidade());

                statement.execute();
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int incluirLocalEntrega(LocalEntrega localEntrega) {
        try (Connection connection = ConexaoDBUtil.getConnection()) {

            connection.setAutoCommit(false);

            String sql = "INSERT INTO endereco (cep, bairro, logradouro, numero, complemento, ponto_referencia) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setString(1, localEntrega.getCep());
            statement.setString(2, localEntrega.getBairro());
            statement.setString(3, localEntrega.getLogradouro());
            statement.setInt(4, localEntrega.getNumero());
            statement.setString(5, localEntrega.getComplemento());
            statement.setString(6, localEntrega.getPontoReferencia());

            statement.executeUpdate();
            ResultSet result  = statement.getGeneratedKeys();
            result.next();

            if (result.getInt(1) > 0) {
                connection.commit();
                return result.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public boolean deletar(int id) {
        try (Connection connection = ConexaoDBUtil.getConnection()) {

            connection.setAutoCommit(false);

            if (this.deletarItensPorIdPedido(id)) {
                String sql = "DELETE FROM pedido WHERE id=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, id);
                statement.executeUpdate();

                if (statement.getUpdateCount() > 0) {
                    int idLocalEntrega = buscarPorId(id).getLocalEntrega().getId();
                    this.deletarLocalEntrega(idLocalEntrega);

                    connection.commit();
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deletarItensPorIdPedido(int idPedido) {
        try (Connection connection = ConexaoDBUtil.getConnection()) {

            connection.setAutoCommit(false);

            String sql = "DELETE FROM item WHERE pedido_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idPedido);

            statement.executeUpdate();

            if (statement.getUpdateCount() > 0) {
                connection.commit();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deletarItem(int id) {
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

    public void deletarLocalEntrega(int id) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {
            connection.setAutoCommit(false);

            String sql = "DELETE FROM endereco WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            statement.executeUpdate();

            if (statement.getUpdateCount() > 0) {
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean atualizarStatus(int id, String status, Date dataFinalizado) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            connection.setAutoCommit(false);

            if (status.equals("Finalizado")) {
                String sql = "UPDATE pedido SET status=?, data_finalizado=? WHERE id=?";
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setString(1, "Finalizado");
                statement.setDate(2, dataFinalizado);
                statement.setInt(3, id);
                statement.executeUpdate();

                if (statement.getUpdateCount() > 0) {
                    connection.commit();
                    return true;
                }
            } else {
                String sql = "UPDATE pedido SET status=? WHERE id=?";
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setString(1, status);
                statement.setInt(2, id);
                statement.executeUpdate();

                if (statement.getUpdateCount() > 0) {
                    connection.commit();
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
