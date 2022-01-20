package bolicho.dao;

import bolicho.model.Endereco;
import bolicho.util.ConexaoDBUtil;

import java.sql.*;

public class EnderecoDAO {

    public Endereco recuperarPeloId(int id) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "SELECT * FROM endereco WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new Endereco(
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

    public int incluir(Endereco endereco) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "INSERT INTO endereco (cep, bairro, logradouro, numero, complemento, ponto_referencia) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setString(1, endereco.getCep());
            statement.setString(2, endereco.getBairro());
            statement.setString(3, endereco.getLogradouro());
            statement.setInt(4, endereco.getNumero());
            statement.setString(5, endereco.getComplemento());
            statement.setString(6, endereco.getPontoReferencia());

            statement.executeUpdate();
            ResultSet result  = statement.getGeneratedKeys();
            result.next();

            if (result.getInt(1) > 0) {
                return result.getInt(1);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return 0;
    }

    public boolean deletar(int id) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {

            String sql = "DELETE FROM endereco WHERE id=?";
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
