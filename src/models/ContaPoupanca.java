package models;

import models.Interfaces.IContaPoupanca;

public class ContaPoupanca extends Conta implements IContaPoupanca {
    public ContaPoupanca(Pessoa titular, Long numeroConta, Double saldo, Long senha) {
        super(titular, numeroConta, saldo, senha);
    }

    @Override
    public void DepositarCP(Double valor, Long senha) {
        if (VerificaSenha(senha)) {
            if (valor > 0) {
                this.setSaldo(this.getSaldo() + valor);
            }
        }
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
