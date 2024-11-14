package models;

import models.Interfaces.IConta;

import java.util.Objects;

public class Conta implements IConta {
    private Pessoa titular;
    private Long numeroConta;
    private Double saldo;
    private Long Senha;

    public Conta(Pessoa titular, Long numeroConta, Double saldo, Long senha) {
        this.titular = titular;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        Senha = senha;
    }

    public Pessoa getTitular() {
        return titular;
    }

    public void setTitular(Pessoa titular) {
        this.titular = titular;
    }

    public Long getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(Long numeroConta) {
        this.numeroConta = numeroConta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    @Override
    public void Depositar(Double valor) {
        setSaldo(this.saldo + valor);
    }

    @Override
    public boolean Sacar(Double valor, Long senha) {
        if (getSaldo() >= valor && VerificaSenha(senha)) {
            setSaldo(this.saldo - valor);
            return true;
        }
        return false;
    }

    @Override
    public Boolean VerificaSenha(Long senha) {
        return Objects.equals(senha, this.Senha);
    }


    @Override
    public Boolean AlteraSenha(Long newSenha, Long senha) {
        if (Objects.equals(senha, this.Senha)) {
            this.Senha = newSenha;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "\n------ Iformações da conta ------" + "\n" +
                "Nome: " + getTitular().getNome() + "\n" +
                "CPF: " + getTitular().getCpf() + "\n" +
                "Numero da Conta: " + getNumeroConta();
    }
}
