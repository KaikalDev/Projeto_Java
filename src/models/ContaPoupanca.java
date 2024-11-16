package models;

import models.Interfaces.IContaPoupanca;

public class ContaPoupanca implements IContaPoupanca {
    private Pessoa titular;
    private Long numeroConta;
    private Double saldo;
    private Long senha;

    public ContaPoupanca(Pessoa titular, Long numeroConta, Double saldo, Long senha) {
        this.titular = titular;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.senha = senha;
    }

    public Pessoa getTitular() {
        return titular;
    }

    public void setTitular(Pessoa titular) {
        this.titular = titular;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Long getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(Long numeroConta) {
        this.numeroConta = numeroConta;
    }

    @Override
    public void DepositarCP(Double valor, Long senha) {
        if (VerificaSenha(senha)) {
            if (valor > 0) {
                this.setSaldo(this.getSaldo() + valor);
            }
        }
    }

    private boolean VerificaSenha(Long senha) {
        return senha.equals(this.senha);
    }

    @Override
    public void SacarCP(Double valor, Long senha) {
        if (VerificaSenha(senha)) {
            if (valor > 0 && this.getSaldo() >= valor) {
                this.setSaldo(this.getSaldo() - valor);
            }
        }
    }
}
