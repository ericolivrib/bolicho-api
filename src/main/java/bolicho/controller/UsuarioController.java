package bolicho.controller;

import bolicho.model.Usuario;
import bolicho.service.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UsuarioController {


    private final UsuarioService service = new UsuarioService();

    @PostMapping
    public Usuario login(@RequestBody Usuario usuario) {
        return this.service.login(usuario);
    }
}
