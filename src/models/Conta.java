package models;

import Utils.Utils;
import Utils.map.Extrato;
import models.Interfaces.IConta;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Conta implements IConta {
    private Pessoa titular;
    private Long numeroConta;
    private Double saldo;
    private Long Senha;
    private ContaPoupanca CP;
    private final Extrato extrato;

    private static Utils utils = new Utils();

    public Conta(Pessoa titular, Long numeroConta, Double saldo, Long senha) {
        this.titular = titular;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        Senha = senha;
        this.CP = new ContaPoupanca(titular, numeroConta, 0.00, senha, this);
        this.extrato = new Extrato();
    }

    public Extrato getExtrato() {
        return this.extrato;
    }

    public String printExtrato(int TotalMeses) {
        return this.extrato.print(TotalMeses);
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
        Transacao transacao = new Transacao(new Date(), valor, false, false, getSaldo(), getCP().getSaldo());
        setSaldo(this.saldo + valor);
        System.out.println(transacao);
        extrato.addToExtrato(transacao);
    }

    @Override
    public boolean Sacar(Double valor, Long senha) {
        if (!VerificaSenha(senha)) {
            utils.error("Senha incorreta", "A senha informada é invalida");
            return false;
        }
        if (getSaldo() < valor) {
            utils.error("Saldo insuficiente", "Não a Saldo suficiente para completar a transação");
            return false;
        }

        Transacao transacao = new Transacao(new Date(), valor, true, false, getSaldo(), getCP().getSaldo());
        setSaldo(this.saldo - valor);
        System.out.println(transacao);
        extrato.addToExtrato(transacao);
        return true;
    }

    @Override
    public Boolean VerificaSenha(Long senha) {
        return Objects.equals(senha, this.Senha);
    }


    @Override
    public void AlteraSenha(Long newSenha, Long senha) {
        if (Objects.equals(senha, this.Senha)) {
            this.Senha = newSenha;
            System.out.println("Senha alterada com sucesso");
        }
    }

    @Override
    public String toString() {
        return "\n------ Iformações da conta ------" + "\n" +
                "Nome: " + getTitular().getNome() + "\n" +
                "CPF: " + getTitular().getCpf() + "\n" +
                "Numero da Conta: " + getNumeroConta();
    }

    @Override
    public String SimularRendimnto(Integer totalMeses) {
        Double saldoSimulacao = this.getSaldo();
        double rendimento = 0.00;

        StringBuilder sb = new StringBuilder();

        sb.append("------ Simulação do rendimento ------\n");
        sb.append("Mês | Rendimento | Saldo\n");

        int mesAtual = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1;

        for (int mes = 0; mes < totalMeses; mes++) {
            int mesSimulacao = (mesAtual + mes - 1) % 12 + 1;

            rendimento = (saldoSimulacao / 100) * 0.05;

            saldoSimulacao += rendimento;

            sb.append(String.format("%2d  | %.2f R$  | %.2f R$\n", mesSimulacao, rendimento, saldoSimulacao));
        }

        return sb.toString();
    }

}
