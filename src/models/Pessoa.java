package models;

import Utils.Utils;

import java.time.LocalDate;
import java.time.Period;

public class Pessoa {
    private final String Nome;
    private Integer idade;
    private final Long cpf;
    private final LocalDate DataNascimento;
    private final Long numeroConta;

    private static final Utils utils = new Utils();

    public Pessoa(String nome, Integer idade, Long cpf, LocalDate dataNascimento, Long numeroConta) {
        Nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        DataNascimento = dataNascimento;
        this.numeroConta = numeroConta;
    }

    public String getNome() {
        return Nome;
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

    public LocalDate getDataNascimento() {
        return DataNascimento;
    }

    public Long getNumeroConta() {
        return numeroConta;
    }

    public void AtualizaIdade() {
        Integer idadeAtual = Period.between(getDataNascimento(), LocalDate.now()).getYears();

        if (!idadeAtual.equals(getIdade())) {
            setIdade(idadeAtual);
        }
    }

    public Conta getConta() {
        return utils.contaMap.consultaNumeroConta(getNumeroConta());
    }
}
