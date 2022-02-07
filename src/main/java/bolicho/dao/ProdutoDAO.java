package bolicho.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import bolicho.model.Produto;
import bolicho.util.ConexaoDBUtil;

public class ProdutoDAO {

    public List<Produto> buscar() {
        List<Produto> produtos = new ArrayList<>();

        try (Connection connection = ConexaoDBUtil.getConnection()) {
            String sql = "SELECT * FROM produto WHERE arquivado=false";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                Produto produto = new Produto(
                        result.getInt("id"),
                        result.getString("descricao"),
                        result.getDouble("preco_unitario"),
                        result.getDouble("qtd_estoque"),
                        result.getString("unidade_medida"),
                        result.getBoolean("arquivado")
                );

                produtos.add(produto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }

    public Produto buscarPorId(int id) {
        Produto produto = null;

        try (Connection connection = ConexaoDBUtil.getConnection()) {
            String sql = "SELECT * FROM produto WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                produto = new Produto(
                        result.getInt("id"),
                        result.getString("descricao"),
                        result.getDouble("preco_unitario"),
                        result.getDouble("qtd_estoque"),
                        result.getString("unidade_medida"),
                        result.getBoolean("arquivado")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produto;
    }

    public Produto incluir(Produto produto) {
        try (Connection connection = ConexaoDBUtil.getConnection()) {
            String sql = "INSERT INTO produto (descricao, preco_unitario, unidade_medida, qtd_estoque, arquivado) " +
                    "VALUES (?, ?, ?, ?, false)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, produto.getDescricao());
            statement.setDouble(2, produto.getPrecoUnitario());
            statement.setString(3, produto.getUnidadeMedida());
            statement.setDouble(4, produto.getQtdEstoque());

            statement.executeUpdate();

            if (statement.getUpdateCount() > 0) {
                return produto;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean atualizar(Produto produto) {
        try (Connection connection = ConexaoDBUtil.getConnection()) {
            String sql = "UPDATE produto SET descricao=?, preco_unitario=?, unidade_medida=? WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, produto.getDescricao());
            statement.setDouble(2, produto.getPrecoUnitario());
            statement.setString(3, produto.getUnidadeMedida());
            statement.setInt(4, produto.getId());

            statement.executeUpdate();

            if (statement.getUpdateCount() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean atualizarEstoque(int id, double quantidade) {
        try (Connection connection = ConexaoDBUtil.getConnection()) {
            String sql = "UPDATE produto SET qtd_estoque=? WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, quantidade);
            statement.setInt(2, id);

            statement.executeUpdate();

            if (statement.getUpdateCount() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean arquivar(int id) {
        try (Connection connection = ConexaoDBUtil.getConnection()) {
            String sql = "UPDATE produto SET arquivado=true WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            statement.executeUpdate();

            if (statement.getUpdateCount() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
