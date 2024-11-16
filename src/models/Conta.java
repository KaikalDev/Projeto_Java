package models;

import Utils.map.Extrato;
import models.Interfaces.IConta;

import java.util.Date;
import java.util.Objects;

public class Conta implements IConta {
    private Pessoa titular;
    private Long numeroConta;
    private Double saldo;
    private Long Senha;
    private ContaPoupanca CP;
    private final Extrato extrato;

    public Conta(Pessoa titular, Long numeroConta, Double saldo, Long senha) {
        this.titular = titular;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        Senha = senha;
        this.CP = new ContaPoupanca(titular, numeroConta, 0.00, senha);
        this.extrato = new Extrato();
    }

    public Extrato getExtrato() {
        return extrato;
    }

    public String setExtrato(int TotalMeses) {
        return extrato.toString(TotalMeses, getSaldo(), getCP().getSaldo());
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

    public Long getSenha() {
        return Senha;
    }

    public void setSenha(Long senha) {
        Senha = senha;
    }

    public ContaPoupanca getCP() {
        return CP;
    }

    public void setCP(ContaPoupanca CP) {
        this.CP = CP;
    }

    @Override
    public void Depositar(Double valor) {
        setSaldo(this.saldo + valor);
        Transacao transacao = new Transacao(new Date(), valor, false, false);
        System.out.println(transacao);
        extrato.addToExtrato(transacao);
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

    @Override
    public Object[][] SimularRendimnto(Integer totalMeses) {
        if (totalMeses <= 12 && this.getSaldo() >= 100) {
            /* Erro o maximo é 12 meses */
            /* Info do saldo < 100 */
            Object[][] matriz = new Object[totalMeses][2];
            Double saldoSimulacao = this.getSaldo();
            double rendimento = 0.00;

            for (int mes = 1; mes <= totalMeses; mes++) {
                if (saldoSimulacao > 100) {
                    rendimento += (saldoSimulacao / 100) * 0.05;
                    saldoSimulacao += rendimento;
                }
                matriz[mes][0] = mes;
                matriz[mes][1] = saldoSimulacao;
            }

            return matriz;
        }
        return null;
    }
}
