package bolicho.dao;

import bolicho.model.Usuario;
import bolicho.util.ConexaoDBUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario buscar(Usuario usuario) {

        try (Connection connection = ConexaoDBUtil.getConnection()) {
            String sql = "SELECT * FROM usuario WHERE email=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, usuario.getEmail());
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new Usuario(result.getString("email"),
                        new BCryptPasswordEncoder().encode(result.getString("senha")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
