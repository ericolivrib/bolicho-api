package bolicho.security;

import bolicho.dao.UsuarioDAO;
import bolicho.model.Usuario;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = new UsuarioDAO().buscar(new Usuario(email, null, null, null));

        if (usuario == null) {
            throw new UsernameNotFoundException("Credenciais inválidas");
        } else {
            return User.withUsername(usuario.getEmail())
                    .password(usuario.getSenha())
                    .authorities(usuario.getPermissao()).build();
        }
    }
}
