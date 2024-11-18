package models;

import Utils.Utils;

import java.util.Date;

public class ContaPoupanca {
    private Double saldo;
    private final Conta contaCorrente;

    private static final Utils utils = new Utils();

    public ContaPoupanca(Double saldo, Conta ContaC) {
        this.saldo = saldo;
        this.contaCorrente = ContaC;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Conta getContaCorrente() {
        return contaCorrente;
    }

    public void DepositarCP(Double valor, Long senha) {
        if (isInvalid(senha)) {
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

    private boolean isInvalid(Long senha) {
        return !this.contaCorrente.VerificaSenha(senha);
    }

    public void SacarCP(Double valor, Long senha) {
        if (isInvalid(senha)) {
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
