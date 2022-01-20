package bolicho.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import bolicho.model.Produto;
import bolicho.util.ConexaoDBUtil;

public class ProdutoDAO {

    public List<Produto> recuperar() {

        List<Produto> produtos = new ArrayList<>();

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "SELECT * FROM produto";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Produto produto = new Produto(
                        resultSet.getInt("id"),
                        resultSet.getString("descricao"),
                        resultSet.getBigDecimal("preco_unitario"),
                        resultSet.getString("unidade_medida"),
                        resultSet.getInt("qtd_estoque"),
                        resultSet.getBoolean("arquivado")
                );

                produtos.add(produto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }

    public Produto recuperarPorId(int id) {

        Produto produto = new Produto();

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "SELECT * FROM produto WHERE id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                produto = new Produto(
                        resultSet.getInt("id"),
                        resultSet.getString("descricao"),
                        resultSet.getBigDecimal("preco_unitario"),
                        resultSet.getString("unidade_medida"),
                        resultSet.getInt("qtd_estoque"),
                        resultSet.getBoolean("arquivado")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produto;
    }

    public boolean inserir(Produto produto) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "INSERT INTO produto (descricao, preco_unitario, unidade_medida, arquivado) " +
                    "VALUES (?, ?, ?, false)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, produto.getDescricao());
            preparedStatement.setBigDecimal(2, produto.getPrecoUnitario());
            preparedStatement.setString(3, produto.getUnidadeMedida());

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizar(Produto produto) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "UPDATE produto SET descricao=?, preco_unitario=?, unidade_medida=? WHERE id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, produto.getDescricao());
            preparedStatement.setBigDecimal(2, produto.getPrecoUnitario());
            preparedStatement.setString(3, produto.getUnidadeMedida());
            preparedStatement.setInt(4, produto.getId());

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarEstoque(Produto produto) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "UPDATE produto SET qtd_estoque=? WHERE id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, produto.getQtdEstoque());
            preparedStatement.setInt(2, produto.getId());

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean arquivar(int id) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "UPDATE produto SET arquivado=true WHERE id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
