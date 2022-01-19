package bolicho.dao;

import bolicho.model.Endereco;

import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO {

    private final List<Endereco> enderecos = new ArrayList<>();

    public EnderecoDAO() {
        this.enderecos.add(new Endereco(
                1,
                "99999-999",
                "Bairro",
                "Logradouro",
                99,
                "Complemento",
                "Ponto de referência"));
    }

    public List<Endereco> getEnderecos() {
        return this.enderecos;
    }

    public Endereco getEnderecoByID(int idEndereco) {

        for (Endereco e : this.enderecos) {
            if (idEndereco == e.getId()) {
                return e;
            }
        }

        return null;
    }

    public int cadastrarEndereco(Endereco e) {
        e.setId(this.enderecos.size() + 1);
        this.enderecos.add(e);
        return e.getId();
    }

    public boolean deletarEndereco(int idEndereco) {
        return this.enderecos.removeIf(p -> idEndereco == p.getId());
    }
}
