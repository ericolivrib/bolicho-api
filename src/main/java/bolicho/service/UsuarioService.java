package bolicho.service;

import bolicho.dao.UsuarioDAO;
import bolicho.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioDAO dao = new UsuarioDAO();

    public Usuario login(Usuario usuario) {
        return this.dao.buscar(usuario);
    }
}
