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
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                Produto produto = new Produto(
                        result.getInt("id"),
                        result.getString("descricao"),
                        result.getBigDecimal("preco_unitario"),
                        result.getString("unidade_medida"),
                        result.getInt("qtd_estoque"),
                        result.getBoolean("arquivado")
                );

                produtos.add(produto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }

    public Produto recuperarPeloId(int id) {

        Produto produto = new Produto();

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "SELECT * FROM produto WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                produto = new Produto(
                        result.getInt("id"),
                        result.getString("descricao"),
                        result.getBigDecimal("preco_unitario"),
                        result.getString("unidade_medida"),
                        result.getInt("qtd_estoque"),
                        result.getBoolean("arquivado")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produto;
    }

    public boolean incluir(Produto produto) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "INSERT INTO produto (descricao, preco_unitario, unidade_medida, arquivado) " +
                    "VALUES (?, ?, ?, false)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, produto.getDescricao());
            statement.setBigDecimal(2, produto.getPrecoUnitario());
            statement.setString(3, produto.getUnidadeMedida());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizar(Produto produto) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "UPDATE produto SET descricao=?, preco_unitario=?, unidade_medida=? WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, produto.getDescricao());
            statement.setBigDecimal(2, produto.getPrecoUnitario());
            statement.setString(3, produto.getUnidadeMedida());
            statement.setInt(4, produto.getId());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarEstoque(Produto produto) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "UPDATE produto SET qtd_estoque=? WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, produto.getQtdEstoque());
            statement.setInt(2, produto.getId());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean arquivar(int id) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "UPDATE produto SET arquivado=true WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
