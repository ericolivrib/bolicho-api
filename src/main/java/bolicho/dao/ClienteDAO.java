package bolicho.dao;

import bolicho.model.Cliente;
import bolicho.util.ConexaoDBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public List<Cliente> buscar() {
        List<Cliente> clientes = new ArrayList<>();

        try (Connection connection = ConexaoDBUtil.getConnection()) {
            String sql = "SELECT * FROM cliente WHERE ativo=true";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                Cliente cliente = new Cliente(
                        result.getInt("id"),
                        result.getString("nome"),
                        result.getString("email"),
                        result.getString("telefone"),
                        result.getString("cpf"),
                        result.getBoolean("ativo")
                );

                clientes.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    public Cliente buscarPorId(int id) {
        try (Connection connection = ConexaoDBUtil.getConnection()) {
            String sql = "SELECT * FROM cliente WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new Cliente(
                        result.getInt("id"),
                        result.getString("nome"),
                        result.getString("email"),
                        result.getString("telefone"),
                        result.getString("cpf"),
                        result.getBoolean("ativo")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Cliente incluir(Cliente cliente) {
        try (Connection connection = ConexaoDBUtil.getConnection()) {
            String sql = "INSERT INTO cliente (nome, email, telefone, cpf, ativo) " +
                    "VALUES (?, ?, ?, ?, true)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getEmail());
            statement.setString(3, cliente.getTelefone());
            statement.setString(4, cliente.getCpf());

            statement.executeUpdate();
            return cliente;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean atualizar(Cliente cliente) {
        try (Connection connection = ConexaoDBUtil.getConnection()) {
            String sql = "UPDATE cliente SET nome=?, email=?, telefone=?, cpf=? WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getEmail());
            statement.setString(3, cliente.getTelefone());
            statement.setString(4, cliente.getCpf());
            statement.setInt(5, cliente.getId());

            statement.executeUpdate();

            if (statement.getUpdateCount() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean desativar(int id) {
        try (Connection connection = ConexaoDBUtil.getConnection()) {
            String sql = "UPDATE cliente SET ativo=false WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            statement.executeUpdate();

            if (statement.getUpdateCount() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }
}
