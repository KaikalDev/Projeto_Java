package models;

import Utils.Utils;
import models.Interfaces.IContaPoupanca;

import java.util.Date;

public class ContaPoupanca implements IContaPoupanca {
    private Pessoa titular;
    private Long numeroConta;
    private Double saldo;
    private Long senha;
    private Conta contaCorrente;

    private static Utils utils = new Utils();

    public ContaPoupanca(Pessoa titular, Long numeroConta, Double saldo, Long senha, Conta ContaC) {
        this.titular = titular;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.senha = senha;
        this.contaCorrente = ContaC;
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

    public Conta getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(Conta contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    @Override
    public void DepositarCP(Double valor, Long senha) {
        if (!VerificaSenha(senha)) {
            utils.error("Senha incorreta", "A senha informada é invalida");
            return;
        }
        boolean res = contaCorrente.Sacar(valor, senha);
        if (res) {
            Transacao transacao = new Transacao(new Date(), valor, false, true, getContaCorrente().getSaldo(), getSaldo());
            setSaldo(saldo + valor);
            System.out.println(transacao);
            contaCorrente.getExtrato().addToExtrato(transacao);
        }
    }

    private boolean VerificaSenha(Long senha) {
        return senha.equals(this.senha);
    }

    @Override
    public void SacarCP(Double valor, Long senha) {
        if (!VerificaSenha(senha)) {
            utils.error("Senha incorreta", "A senha informada é invalida");
            return;
        }
        if (this.getSaldo() < valor) {
            utils.error("Saldo insuficiente", "Não a Saldo suficiente para completar a transação");
            return;
        }
        Transacao transacao = new Transacao(new Date(), valor, true, true, getContaCorrente().getSaldo(), getSaldo());
        this.setSaldo(this.getSaldo() - valor);
        contaCorrente.Depositar(valor);
        System.out.println(transacao);
        contaCorrente.getExtrato().addToExtrato(transacao);
    }
}
