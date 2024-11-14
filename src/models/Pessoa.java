package models;

import java.util.Objects;

public class Pessoa {
    private String Nome;
    private Integer idade;
    private Long cpf;

    public Pessoa(String nome, Integer idade, Long cpf) {
        Nome = nome;
        this.idade = idade;
        this.cpf = cpf;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }
}
